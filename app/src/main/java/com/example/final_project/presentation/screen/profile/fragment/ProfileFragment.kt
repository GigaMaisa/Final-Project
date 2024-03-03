package com.example.final_project.presentation.screen.profile.fragment

import android.util.Log.d
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.R
import com.example.final_project.databinding.FragmentProfileBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.ProfileNavigationUiEvents
import com.example.final_project.presentation.screen.profile.adapter.ProfileFavouritesRecyclerViewAdapter
import com.example.final_project.presentation.screen.profile.viewmodel.ProfileViewModel
import com.example.final_project.presentation.state.SignOutState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModels()
    private val favouritesAdapter = ProfileFavouritesRecyclerViewAdapter()
    override fun setUp() {
        setUpRecycler()
        binding.switchMaterial.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    }

    override fun setUpListeners() {
        binding.interactiveImage.setOnClickListener {
            d("itInteracts", "CLICKED")
        }

        binding.switchMaterial.setOnCheckedChangeListener { _, isChecked ->
            changeDarkMode(isChecked)
        }

        binding.btnLogout.setOnClickListener {
            viewModel.onEvent(ProfileViewModel.ProfileEvent.SignOutEvent)
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
            }
        }
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
        if (state.isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            View.GONE
        }

        state.errorMessage?.let {
            progressBar.visibility = View.GONE
        }
    }

    private fun handleNavigationEvents(event: ProfileNavigationUiEvents) {
        when (event) {
            is ProfileNavigationUiEvents.NavigateToLogIn -> {
                requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.loginFragment)
            }
        }
    }
}