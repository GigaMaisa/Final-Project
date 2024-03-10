package com.example.final_project.presentation.screen.profile.fragment

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.util.Log.d
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.final_project.R
import com.example.final_project.databinding.FragmentProfileBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.ProfileNavigationUiEvents
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.extension.showSnackBar
import com.example.final_project.presentation.screen.profile.adapter.ProfileFavouritesRecyclerViewAdapter
import com.example.final_project.presentation.screen.profile.viewmodel.ProfileViewModel
import com.example.final_project.presentation.screen.profile.viewmodel.SettingsEvents
import com.example.final_project.presentation.screen.profile.viewmodel.SettingsViewModel
import com.example.final_project.presentation.state.ProfileState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
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
        settingsViewModel.onEvent(SettingsEvents.GetLanguageEvent)
    }

    override fun setUpListeners() = with(binding) {
        ivPhoto.setOnClickListener {
            d("itInteracts", "CLICKED")
        }

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

                var currentLanguage: String? = null

                launch {
                    settingsViewModel.languageFlow.collect {language ->
                        if (language != currentLanguage) {
                            currentLanguage = language
                            applyLanguageSetting(language)
                        }
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

    private fun applyLanguageSetting(language: String) {
        val lang = if (language == "English" ||  language == "ინგლისური") "en" else "ka"
        val locale = Locale(lang)

        val resources = requireContext().resources
        val configuration = resources.configuration
        configuration.setLocale(locale)

        val displayMetrics = resources.displayMetrics
        resources.updateConfiguration(configuration, displayMetrics)

        if (configuration.locale != Locale.getDefault()) {
            requireActivity()
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        pickImageResultLauncher.launch(intent)
    }

    private fun changeDarkMode(isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun getStringResource(resourceId: Int): String {
        return resources.getString(resourceId)
    }

    private fun handleNavigationEvents(event: ProfileNavigationUiEvents) {
        when (event) {
            is ProfileNavigationUiEvents.NavigateToLogIn -> {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.placeholderDestination, true)
                    .build()

                requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.loginFragment, null, navOptions)
            }

            is ProfileNavigationUiEvents.NavigateToLocation -> {
                findNavController().navigate(ProfileFragmentDirections.actionProfilePageToDeliveryLocationFragment())
            }

            ProfileNavigationUiEvents.NavigateToPayment -> {
                findNavController().navigate(ProfileFragmentDirections.actionProfilePageToCardFragment())
            }
        }
    }

    private fun isSystemDarkModeOn(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }
}