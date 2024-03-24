package com.example.final_project.presentation.screen.profile.fragment

import android.app.Activity
import android.content.Intent
import android.util.Log.d
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.final_project.R
import com.example.final_project.databinding.FragmentProfileBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.ProfileNavigationUiEvents
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.extension.showSnackBar
import com.example.final_project.presentation.screen.bottom_nav_container.BottomNavContainerFragmentDirections
import com.example.final_project.presentation.screen.profile.adapter.ProfileFavouritesRecyclerViewAdapter
import com.example.final_project.presentation.screen.profile.viewmodel.ProfileViewModel
import com.example.final_project.presentation.state.ProfileState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModels()
    private val favouritesAdapter = ProfileFavouritesRecyclerViewAdapter()

    private val pickImageResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImageUri = result.data?.data
            selectedImageUri?.let {
                viewModel.onEvent(ProfileViewModel.ProfileEvent.UploadPhotoEvent(it))
            }
        }
    }

    override fun setUp() {
        viewModel.onEvent(ProfileViewModel.ProfileEvent.GetPhotoEvent)
        viewModel.onEvent(ProfileViewModel.ProfileEvent.GetUserDataEvent)
    }

    override fun setUpListeners() = with(binding) {
        btnLogOut.setOnClickListener {
            viewModel.onEvent(ProfileViewModel.ProfileEvent.SignOutEvent)
        }

        ivPhoto.setOnClickListener {
            selectImage()
        }

        tvPayment.setOnClickListener {
            viewModel.onUiEvent(ProfileNavigationUiEvents.NavigateToPayment)
        }

        tvLocation.setOnClickListener {
            viewModel.onUiEvent(ProfileNavigationUiEvents.NavigateToLocation)
        }

        tvSettings.setOnClickListener {
            viewModel.onUiEvent(ProfileNavigationUiEvents.NavigateToSettings)
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.profileStateFlow.collect {
                        handleState(it)
                    }
                }

                launch {
                    viewModel.uiEvent.collect {
                        handleNavigationEvents(it)
                    }
                }
            }
        }
    }

    private fun handleState(state: ProfileState) = with(state) {
        with(binding) {
            progressBar.isVisible = isLoading

            favourites?.let { favourites ->
                favouritesAdapter.submitList(favourites)
            }

            errorMessage?.let {
                progressBar.visibility = View.GONE
                requireView().showSnackBar(getStringResource(it))
            }

            userData?.let {
                tvTitle.text = it.fullName
                tvDescription.text = it.email
                tvPhoneNumber.text = it.phoneNumber
            }

            state.imageRetrieve?.let {
                if (it != "") {
                    ivPhoto.loadImage(it)
                }
            }
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        pickImageResultLauncher.launch(intent)
    }

    private fun getStringResource(resourceId: Int): String {
        return resources.getString(resourceId)
    }

    private fun handleNavigationEvents(event: ProfileNavigationUiEvents) {
        when (event) {
            is ProfileNavigationUiEvents.NavigateToLogIn -> {
                requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_to_welcomeFragment)
            }

            is ProfileNavigationUiEvents.NavigateToLocation -> {
                requireActivity().findNavController(R.id.nested_nav_host_fragment).navigate(BottomNavContainerFragmentDirections.actionBottomNavPlaceHolderToDeliveryLocationFragment())
            }

            is ProfileNavigationUiEvents.NavigateToPayment -> {
                requireActivity().findNavController(R.id.nested_nav_host_fragment).navigate(BottomNavContainerFragmentDirections.actionBottomNavPlaceHolderToCardFragment())
            }

            is ProfileNavigationUiEvents.NavigateToSettings ->
                requireActivity().findNavController(R.id.nested_nav_host_fragment).navigate(BottomNavContainerFragmentDirections.actionBottomNavPlaceHolderToSettingsFragment())
        }
    }
}