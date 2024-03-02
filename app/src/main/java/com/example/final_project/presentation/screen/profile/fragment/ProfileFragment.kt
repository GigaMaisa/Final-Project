package com.example.final_project.presentation.screen.profile.fragment

import android.util.Log.d
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.databinding.FragmentProfileBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.screen.profile.adapter.ProfileFavouritesRecyclerViewAdapter
import com.example.final_project.presentation.screen.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

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
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileStateFlow.collect {
                    it.favourites?.let { favourites ->
                        favouritesAdapter.submitList(favourites)
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
}