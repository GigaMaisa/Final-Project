package com.example.final_project.presentation.screen.restoraunt_details.bottomsheet

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.databinding.RestaurantDetailsBottomSheetDialogBinding
import com.example.final_project.presentation.event.restaurant.RestaurantMenuItemEvent
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.model.restaurant.MenuItemDetails
import com.example.final_project.presentation.screen.restoraunt_details.adapter.RestaurantOptionRecyclerViewAdapter
import com.example.final_project.presentation.state.RestaurantMenuItemState
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RestaurantBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: RestaurantDetailsBottomSheetDialogBinding
    private val restaurantMenuAdapter = RestaurantOptionRecyclerViewAdapter()
    private val viewModel: RestaurantMenuItemViewModel by viewModels()
    var menuItemDetails: MenuItemDetails? = null

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

        submitMenuItemData(menuItemDetails!!)
        setUpRecycler()
        setUpObservers()
        setUpBottomSheetBehavior()
    }

    private fun setUpRecycler() = with(binding.recyclerOptions) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = restaurantMenuAdapter
        restaurantMenuAdapter.onOptionClick = {
            viewModel.onEvent(RestaurantMenuItemEvent.UpdateRestaurantDetailsEvent(it))
        }
    }

    private fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.restaurantMenuItemStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: RestaurantMenuItemState){
        with(state) {
            menuItem?.let {
                with(binding) {
                    tvTitle.text = it.name
                    tvDescription.text = it.description
                    imageViewCover.loadImage(it.image)
                    tvPrice.text = it.price.toString().plus(" ₾")
                }
                restaurantMenuAdapter.submitList(it.menuItemAdditions)
            }
        }
    }

    private fun setUpBottomSheetBehavior() {
        val bottomSheetBehavior = BottomSheetBehavior.from(view?.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    private fun submitMenuItemData(menuItemDetails: MenuItemDetails) {
        d("menuItemDetailsBro", menuItemDetails.menuItemAdditions.size.toString())
        viewModel.onEvent(RestaurantMenuItemEvent.SetRestaurantDetailsEvent(menuItemDetails))
    }

    companion object {
        const val RESTAURANT_MENU_BOTTOM_SHEET = "RestaurantMenuBottomSheet"
    }
}