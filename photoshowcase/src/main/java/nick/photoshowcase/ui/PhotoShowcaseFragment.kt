package nick.photoshowcase.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_photo_showcase.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import nick.core.Logger
import nick.core.Resource
import nick.data.models.Photo
import nick.photoshowcase.R
import nick.photoshowcase.vm.PhotoShowcaseViewModel
import nick.uiutils.StaggeredItemDecoration
import nick.uiutils.gone
import nick.uiutils.viewModel
import nick.uiutils.visible
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
    private var photoFetchingJob: Job? = null

    init {
        refreshPhotos()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnPhotoClickedListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        swipe_refresh_layout.setOnRefreshListener {
            refreshPhotos()
        }
    }

    fun refreshPhotos() {
        fetchPhotos { viewModel.refresh() }
    }

    override fun paginate() {
        fetchPhotos { viewModel.paginate() }
    }

    private fun fetchPhotos(block: suspend () -> Flow<Resource<List<Photo>>>) {
        photoFetchingJob?.cancel()
        photoFetchingJob = lifecycleScope.launchWhenStarted {
            block().collect {
                handlePhotos(it)
            }
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
        }
    }

    fun handlePhotos(resource: Resource<List<Photo>>) {
        error.gone()

        when (resource) {
            is Resource.Loading -> {
                swipe_refresh_layout.isRefreshing = true
                resource.data?.let(::submitList)
            }
            is Resource.Success -> {
                swipe_refresh_layout.isRefreshing = false
                resource.data?.let(::emptyResultsHandlingSubmission)
            }
            is Resource.Error -> {
                swipe_refresh_layout.isRefreshing = false
                resource.data?.let(::emptyResultsHandlingSubmission)
                Snackbar.make(requireView(), "Something went terribly wrong: ${resource.throwable.message}", Snackbar.LENGTH_LONG)
                    .show()
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

    override fun onPhotoClicked(id: Long) {
        logger.d("Photo with id $id clicked")
        listener.onPhotoClicked(id)
    }
}