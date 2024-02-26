package com.example.final_project.presentation.screen.profile.fragment

import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.final_project.databinding.FragmentProfileBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.screen.profile.bottomsheet.ProfileBottomSheet
import com.example.final_project.presentation.screen.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val modalBottomSheet = ProfileBottomSheet()
    private val viewModel: ProfileViewModel by viewModels()
    override fun setUp() {
        setUpBottomSheet()
    }

    override fun setUpListeners() {
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileStateFlow.collect {
                    it.favourites?.let { favourites ->
                        modalBottomSheet.submitDataToRecycler(favourites)
                    }
                }
            }
        }
    }

    private fun setUpBottomSheet() {
        modalBottomSheet.show(parentFragmentManager, ProfileBottomSheet.Profile_BOTTOM_SHEET)
        modalBottomSheet.isCancelable = false
        modalBottomSheet.switchListener = {
            modalBottomSheet.dismiss()
            changeDarkMode(it)
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