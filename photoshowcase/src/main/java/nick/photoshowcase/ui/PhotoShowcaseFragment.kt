package nick.photoshowcase.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_photo_showcase.*
import nick.core.Logger
import nick.core.Resource
import nick.data.models.Photo
import nick.photoshowcase.R
import nick.photoshowcase.vm.PhotoShowcaseViewModel
import nick.uiutils.*
import javax.inject.Inject

class PhotoShowcaseFragment @Inject constructor(
    viewModel: PhotoShowcaseViewModel,
    private val logger: Logger
) : Fragment(R.layout.fragment_photo_showcase), OnPhotoClickedListener {

    private val viewModel: PhotoShowcaseViewModel by viewModel { viewModel }
    private val adapter = PhotoShowcaseAdapter(this)

    private lateinit var listener: OnPhotoClickedListener
    private lateinit var paginatingScrollListener: StaggeredPaginatingScrollListener
    private lateinit var itemDecoration: StaggeredItemDecoration

    override fun onAttach(context: Context) {
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
        observePhotos()
        observeErrors()
    }

    fun setUpRecyclerView() {
        val spanCount = resources.getInteger(R.integer.span_count)
        logger.d("RV span count: $spanCount")

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

    fun observePhotos() {
        viewModel.photos.observe(viewLifecycleOwner) {
            error.gone()

            when (it) {
                is Resource.Loading -> {
                    swipe_refresh_layout.isRefreshing = true
                    it.data?.let(::submitList)
                }
                is Resource.Success, is Resource.Error -> {
                    swipe_refresh_layout.isRefreshing = false
                    it.data?.let(::emptyResultsHandlingSubmission)
                }
            }
        }
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
        viewModel.error.observe(viewLifecycleOwner) {
            it ?: return@observe

            it.getContentIfNotHandled()?.let { throwable ->
                Snackbar.make(view!!, "Something went terribly wrong: ${throwable.message}", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
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