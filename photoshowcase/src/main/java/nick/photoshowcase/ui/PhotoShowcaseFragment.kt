package nick.photoshowcase.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_photo_showcase.*
import nick.core.Resource
import nick.data.models.Photo
import nick.photoshowcase.R
import nick.photoshowcase.vm.PhotoShowcaseViewModel
import nick.uiutils.StaggeredItemDecoration
import nick.uiutils.StaggeredPaginatingScrollListener
import nick.uiutils.gone
import nick.uiutils.visible
import javax.inject.Inject

class PhotoShowcaseFragment
    : Fragment(R.layout.fragment_photo_showcase)
    , HasAndroidInjector
    , OnPhotoClickedListener {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var listener: OnPhotoClickedListener
    private val viewModel by viewModels<PhotoShowcaseViewModel> { factory }
    private val adapter = PhotoShowcaseAdapter(this)
    private lateinit var paginatingScrollListener: StaggeredPaginatingScrollListener
    private lateinit var itemDecoration: StaggeredItemDecoration

    override fun androidInjector() = androidInjector

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        listener = context as OnPhotoClickedListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paginatingScrollListener = StaggeredPaginatingScrollListener(viewModel.pageSize, ::paginate)
        itemDecoration = StaggeredItemDecoration(resources.getDimension(R.dimen.photo_margin).toInt())

        if (savedInstanceState == null || viewModel.photos.value == null) {
            viewModel.refresh()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        swipe_refresh_layout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    fun setUpRecyclerView() {
        val spanCount = resources.getInteger(R.integer.span_count)
        with(recycler_view) {
            layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
            adapter = this@PhotoShowcaseFragment.adapter

            if (itemDecorationCount == 0) {
                addItemDecoration(itemDecoration)
            }

            clearOnScrollListeners()
            addOnScrollListener(paginatingScrollListener)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observePhotos()
        observeErrors()
    }

    fun observePhotos() {
        viewModel.photos.observe(viewLifecycleOwner, Observer {
            error.gone()

            when (it) {
                is Resource.Loading -> {
                    swipe_refresh_layout.isRefreshing = true
                    it.data?.let(::submitList)
                }
                is Resource.Success -> {
                    swipe_refresh_layout.isRefreshing = false
                    it.data?.let(::emptyResultsHandlingSubmission)
                }
                is Resource.Error -> {
                    swipe_refresh_layout.isRefreshing = false
                    it.data?.let(::emptyResultsHandlingSubmission)
                }
            }
        })
    }

    fun emptyResultsHandlingSubmission(photos: List<Photo>) {
        if (photos.isEmpty() && adapter.itemCount == 0) {
            error.visible()
        } else {
            submitList(photos)
        }
    }

    fun submitList(photos: List<Photo>) {
        adapter.submitList(photos)
    }

    fun observeErrors() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer

            it.getContentIfNotHandled()?.let { throwable ->
                Snackbar.make(view!!, "Something went terribly wrong: ${throwable.message}", Snackbar.LENGTH_LONG)
                    .show()
            }
        })
    }

    override fun onPhotoClicked(id: Long) {
        listener.onPhotoClicked(id)
    }

    fun paginate() {
        recycler_view.post {
            viewModel.paginate()
        }
    }
}