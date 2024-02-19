package com.example.final_project.presentation.screen.restoraunt_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.final_project.databinding.RestaurantDetailsBottomSheetDialogBinding
import com.example.final_project.presentation.model.RestaurantMenu
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RestaurantBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: RestaurantDetailsBottomSheetDialogBinding
    private val restaurantMenuAdapter = RestaurantMenuRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RestaurantDetailsBottomSheetDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycler()
        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        bottomSheetBehavior.peekHeight = 800
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    fun submitDataToRecycler(list: List<RestaurantMenu>) {
        restaurantMenuAdapter.submitList(list)
    }

    private fun setUpRecycler() = with(binding.recyclerViewMenu) {
        layoutManager = GridLayoutManager(context, 2)
        adapter = restaurantMenuAdapter
    }

    companion object {
        const val RESTAURANT_BOTTOM_SHEET = "RestaurantBottomSheet"
    }
}