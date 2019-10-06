package nick.photodetails.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionManager
import coil.Coil
import coil.api.get
import kotlinx.android.synthetic.main.back.*
import kotlinx.android.synthetic.main.details_bottom_row.*
import kotlinx.android.synthetic.main.fragment_photo_details_with_controls.*
import kotlinx.android.synthetic.main.share.*
import kotlinx.coroutines.launch
import nick.data.models.Photo
import nick.photodetails.R
import nick.photodetails.vm.PhotoDetailsViewModel
import nick.uiutils.ThumbnailZoomCoordinator
import nick.uiutils.gone
import nick.uiutils.viewModel
import javax.inject.Inject

class PhotoDetailsFragment @Inject constructor(
    viewModel: PhotoDetailsViewModel,
    private val thumbnailZoomCoordinator: ThumbnailZoomCoordinator
) : Fragment(R.layout.fragment_photo_details_with_controls) {

    private val viewModel: PhotoDetailsViewModel by viewModel { viewModel }
    private val args: PhotoDetailsFragmentArgs by navArgs()
    private val withControls = ConstraintSet()
    private val withoutControls = ConstraintSet()
    private var isShowingControls = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.queryPhoto(args.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back.setOnClickListener { requireActivity().onBackPressed() }
        setUpConstraintSetAnimations(view.findViewById(R.id.constraint_layout))
        observePhoto()
    }

    fun setUpConstraintSetAnimations(constraintLayout: ConstraintLayout) {
        withControls.clone(constraintLayout)
        withoutControls.clone(requireContext(), R.layout.fragment_photo_details_without_controls)

        photo.setOnPhotoTapListener { _, _, _ ->
            TransitionManager.beginDelayedTransition(constraintLayout)
            if (isShowingControls) {
                withoutControls.applyTo(constraintLayout)
            } else {
                withControls.applyTo(constraintLayout)
            }
            isShowingControls = !isShowingControls
        }
    }

    fun observePhoto() {
        viewModel.photo.observe(viewLifecycleOwner) {
            it ?: return@observe

            loadImageWithThumbnail(it)

            title.text = it.name
            if (it.description.isBlank()) {
                description.gone()
            } else {
                description.text = it.description
            }

            setUpShareButton(dataToShare = it.largeImage)
        }
    }

    private fun loadImageWithThumbnail(photo: Photo) {
        viewLifecycleOwner.lifecycleScope.launch {
            setImageDrawable(Coil.get(photo.smallImage))
            setImageDrawable(Coil.get(photo.largeImage))
        }
    }

    private fun setImageDrawable(drawable: Drawable) {
        thumbnailZoomCoordinator.setImageDrawable(photo, drawable)
    }

    fun setUpShareButton(dataToShare: String) {
        share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, dataToShare)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
}