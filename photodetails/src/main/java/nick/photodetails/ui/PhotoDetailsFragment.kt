package nick.photodetails.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.back.*
import kotlinx.android.synthetic.main.details_bottom_row.*
import kotlinx.android.synthetic.main.fragment_photo_details_with_controls.*
import kotlinx.android.synthetic.main.share.*
import nick.photodetails.R
import nick.photodetails.di.PhotoDetailsDependencies
import nick.photodetails.di.PhotoDetailsDependenciesProvider
import nick.photodetails.vm.PhotoDetailsViewModel
import nick.uiutils.GlideApp
import nick.uiutils.gone
import nick.uiutils.viewModel

class PhotoDetailsFragment
    : Fragment(R.layout.fragment_photo_details_with_controls) {

    private val dependencies: PhotoDetailsDependencies
        get() = (requireActivity().application as PhotoDetailsDependenciesProvider).photoDetailsDependencies
    private val viewModel: PhotoDetailsViewModel by viewModel { dependencies.photoDetailsViewModel }
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
        viewModel.photo.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer

            GlideApp.with(this)
                .load(it.largeImage)
                .thumbnail(GlideApp.with(this).load(it.smallImage))
                .into(photo)

            title.text = it.name
            if (it.description.isBlank()) {
                description.gone()
            } else {
                description.text = it.description
            }

            setUpShareButton(dataToShare = it.largeImage)
        })
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