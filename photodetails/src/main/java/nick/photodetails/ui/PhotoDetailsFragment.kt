package nick.photodetails.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionManager
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.back.*
import kotlinx.android.synthetic.main.details_bottom_row.*
import kotlinx.android.synthetic.main.fragment_photo_details_with_controls.*
import kotlinx.android.synthetic.main.share.*
import nick.core.Logger
import nick.photodetails.R
import nick.photodetails.vm.PhotoDetailsViewModel
import nick.uiutils.GlideApp
import nick.uiutils.gone
import javax.inject.Inject

class PhotoDetailsFragment
    : Fragment(R.layout.fragment_photo_details_with_controls)
    , HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    @Inject
    lateinit var logger: Logger
    private val args: PhotoDetailsFragmentArgs by navArgs()
    private val viewModel by viewModels<PhotoDetailsViewModel> { factory }
    private val withControls = ConstraintSet()
    private val withoutControls = ConstraintSet()
    private var isShowingControls = true

    override fun androidInjector() = androidInjector

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        viewModel.queryPhoto(args.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back.setOnClickListener { requireActivity().onBackPressed() }
        setUpConstraintSetAnimations(view.findViewById(R.id.constraint_layout))
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observePhoto()
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