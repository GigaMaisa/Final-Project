package com.example.final_project.presentation.screen.profile.fragment

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.util.Log.d
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.R
import com.example.final_project.databinding.FragmentProfileBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.ProfileNavigationUiEvents
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.screen.profile.adapter.ProfileFavouritesRecyclerViewAdapter
import com.example.final_project.presentation.screen.profile.viewmodel.ProfileViewModel
import com.example.final_project.presentation.screen.profile.viewmodel.SettingsEvents
import com.example.final_project.presentation.screen.profile.viewmodel.SettingsViewModel
import com.example.final_project.presentation.state.PhotoState
import com.example.final_project.presentation.state.SignOutState
import com.example.final_project.presentation.state.UserDataState
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
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.profileStateFlow.collect {
                        it.favourites?.let { favourites ->
                            favouritesAdapter.submitList(favourites)
                        }
                    }
                }

                launch {
                    viewModel.signOutState.collect {
                        handleSignOutState(it)
                    }
                }

                launch {
                    viewModel.uiEvent.collect {
                        handleNavigationEvents(it)
                    }
                }

                launch {
                    viewModel.imageUploadStatus.collect {
                        handlePhotoState(it)
                    }
                }

                launch {
                    viewModel.userImageFlow.collect {
                        handlePhotoState(it)
                    }
                }

                launch {
                    viewModel.userDataFlow.collect {
                        handleUserDataState(it)
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

    private fun handleUserDataState(state: UserDataState) = with(binding) {
        progressBar.isVisible = state.isLoading

        state.userData?.let {
            tvTitle.text = it.fullName
            tvDescription.text = it.email
            tvPhoneNumber.text = it.phoneNumber
        }
    }

    private fun handlePhotoState(state: PhotoState) = with(binding) {
        progressBar.isVisible = state.isLoading

        state.errorMessage?.let {
            progressBar.visibility = View.GONE
        }

        state.imageUri?.let {
            if (it != "") {
                ivPhoto.loadImage(it)
            }
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

    private fun handleSignOutState(state: SignOutState) = with(binding) {
        progressBar.isVisible = state.isLoading

        state.errorMessage?.let {
            progressBar.visibility = View.GONE
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