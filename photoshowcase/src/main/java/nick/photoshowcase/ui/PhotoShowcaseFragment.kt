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
import nick.photos.localmodels.Photo
import nick.photoshowcase.R
import nick.photoshowcase.vm.PhotoShowcaseViewModel
import nick.uiutils.*
import javax.inject.Inject
import javax.inject.Provider

class PhotoShowcaseFragment @Inject constructor(
    vmProvider: Provider<PhotoShowcaseViewModel>,
    private val logger: Logger
) : Fragment(R.layout.fragment_photo_showcase), PhotoShowcaseCallbacks {

    private val viewModel: PhotoShowcaseViewModel by viewModel { vmProvider.get() }
    private val adapter = PhotoShowcaseAdapter(this)
    private val itemDecoration by lazy {
        StaggeredItemDecoration(resources.getDimension(R.dimen.photo_margin).toInt())
    }
    private lateinit var listener: OnPhotoClickedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnPhotoClickedListener
    }

    fun refresh() {
        viewModel.refresh()
    }

    override fun paginate() {
        viewModel.paginate()
    }

    override fun paginationThreshold() = viewModel.paginationThreshold

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        swipe_refresh_layout.setOnRefreshListener(::refresh)
        observeLoading()
        observePhotos()
        observeErrors()
    }

    fun setUpRecyclerView() {
        val spanCount = resources.getInteger(R.integer.span_count)

        with(recycler_view) {
            layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
            adapter = this@PhotoShowcaseFragment.adapter

            if (itemDecorationCount == 0) {
                addItemDecoration(itemDecoration)
            }
        }
    }

    fun observePhotos() {
        viewModel.photos.observe(viewLifecycleOwner) { photos ->
            val isEmpty = photos.isEmpty() && adapter.itemCount == 0

            if (!isEmpty) {
                submitList(photos)
            }

            empty.visibleOrGone(isEmpty)
        }
    }

    fun observeLoading() {
        viewModel.loading.observe(viewLifecycleOwner) {
            swipe_refresh_layout.isRefreshing = it
        }
    }

    fun observeErrors() {
        viewModel.error.observe(viewLifecycleOwner) { throwable: Throwable? ->
            throwable ?: return@observe

            Snackbar.make(view!!, "Something went terribly wrong: ${throwable.message}", Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry_error_prompt) { viewModel.retry() }
                .show()
        }
    }

    fun submitList(photos: List<Photo>) {
        adapter.submitList(photos)
    }

    override fun onPhotoClicked(id: Long) {
        logger.d("Photo with id $id clicked")
        listener.onPhotoClicked(id)
    }
}