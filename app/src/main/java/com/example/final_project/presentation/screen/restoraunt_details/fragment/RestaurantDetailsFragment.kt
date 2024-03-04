package com.example.final_project.presentation.screen.restoraunt_details.fragment

import androidx.navigation.fragment.findNavController
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
        findNavController().navigate(RestaurantDetailsFragmentDirections.actionRestaurantDetailsFragmentToRestaurantBottomSheet())
    }
}