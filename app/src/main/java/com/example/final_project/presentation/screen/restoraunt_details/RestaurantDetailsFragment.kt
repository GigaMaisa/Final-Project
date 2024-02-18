package com.example.final_project.presentation.screen.restoraunt_details

import com.example.final_project.databinding.FragmentRestorauntDetailsBinding
import com.example.final_project.presentation.base.BaseFragment

class RestaurantDetailsFragment : BaseFragment<FragmentRestorauntDetailsBinding>(FragmentRestorauntDetailsBinding::inflate) {
    override fun setUp() {
        setUpBottomSheet()
    }

    override fun setUpListeners() {
    }

    override fun setUpObservers() {
    }

    private fun setUpBottomSheet() {
        val modalBottomSheet = RestaurantBottomSheet()
        modalBottomSheet.show(parentFragmentManager, RestaurantBottomSheet.RESTAURANT_BOTTOM_SHEET)
        modalBottomSheet.isCancelable = false
    }
}