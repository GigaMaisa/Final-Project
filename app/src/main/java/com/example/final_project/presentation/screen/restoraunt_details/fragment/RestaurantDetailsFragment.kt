package com.example.final_project.presentation.screen.restoraunt_details.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.final_project.databinding.FragmentRestorauntDetailsBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.screen.restoraunt_details.viewmodel.RestaurantDetailsViewModel
import com.example.final_project.presentation.screen.restoraunt_details.bottomsheet.RestaurantBottomSheet
import kotlinx.coroutines.launch

class RestaurantDetailsFragment : BaseFragment<FragmentRestorauntDetailsBinding>(FragmentRestorauntDetailsBinding::inflate) {
    private val viewModel: RestaurantDetailsViewModel by viewModels()
    private val modalBottomSheet = RestaurantBottomSheet()
    override fun setUp() {
        setUpBottomSheet()
    }

    override fun setUpListeners() {
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.restaurantMenuStateFlow.collect {
                    modalBottomSheet.submitDataToRecycler(it)
                }
            }
        }
    }

    private fun setUpBottomSheet() {
        modalBottomSheet.show(parentFragmentManager, RestaurantBottomSheet.RESTAURANT_BOTTOM_SHEET)
        modalBottomSheet.isCancelable = false
    }
}