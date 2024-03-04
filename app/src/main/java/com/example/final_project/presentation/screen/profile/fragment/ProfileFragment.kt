package com.example.final_project.presentation.screen.profile.fragment

import android.app.Activity
import android.content.Intent
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.R
import com.example.final_project.databinding.FragmentProfileBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.ProfileNavigationUiEvents
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.screen.profile.adapter.ProfileFavouritesRecyclerViewAdapter
import com.example.final_project.presentation.screen.profile.viewmodel.ProfileViewModel
import com.example.final_project.presentation.state.PhotoState
import com.example.final_project.presentation.state.SignOutState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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
        setUpRecycler()
        binding.switchMaterial.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        setUpSpinner()
    }

    override fun setUpListeners() = with(binding) {
        imageViewProfile.setOnClickListener {
            d("itInteracts", "CLICKED")
        }

        switchMaterial.setOnCheckedChangeListener { _, isChecked ->
            changeDarkMode(isChecked)
        }

        btnLogOut.setOnClickListener {
            viewModel.onEvent(ProfileViewModel.ProfileEvent.SignOutEvent)
        }

        imageViewProfile.setOnClickListener {
            selectImage()
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
            }
        }
    }

    private fun handlePhotoState(state: PhotoState) = with(binding) {
        progressBar.isVisible = state.isLoading

        state.errorMessage?.let {
            progressBar.visibility = View.GONE
        }

        state.imageUri?.let {
            imageViewProfile.loadImage(it)
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        pickImageResultLauncher.launch(intent)
    }

    private fun setUpRecycler() {
        with(binding.recyclerViewFavourites) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favouritesAdapter
        }
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

    private fun setUpSpinner() {
        val languages = listOf(getStringResource(R.string.english), getStringResource(R.string.georgian))
        val adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLanguages.adapter = adapter

        binding.spinnerLanguages.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = languages[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
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
        }
    }
}