package com.example.final_project.presentation.screen.restoraunt_details.bottomsheet

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.databinding.RestaurantDetailsBottomSheetDialogBinding
import com.example.final_project.presentation.base.BaseBottomSheetFragment
import com.example.final_project.presentation.event.restaurant.RestaurantMenuItemEvent
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.model.restaurant.MenuOrderDetails
import com.example.final_project.presentation.screen.restoraunt_details.adapter.RestaurantOptionRecyclerViewAdapter
import com.example.final_project.presentation.state.RestaurantMenuItemState
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RestaurantBottomSheet : BaseBottomSheetFragment<RestaurantDetailsBottomSheetDialogBinding>(RestaurantDetailsBottomSheetDialogBinding::inflate) {
    private val restaurantMenuAdapter = RestaurantOptionRecyclerViewAdapter()
    private val viewModel: RestaurantMenuItemViewModel by viewModels()
    var menuOrderDetails: MenuOrderDetails? = null

    override fun setUp() {
        setUpRecycler()
    }

    override fun setUpListeners() {
        setUpBottomSheetBehavior()
        submitMenuItemData(menuOrderDetails!!)
        setUpQuantityPlusMinus()
        viewModel.onEvent(RestaurantMenuItemEvent.UpdateTotalPriceEvent)
        binding.tvAddToOrder.setOnClickListener {
            viewModel.onEvent(RestaurantMenuItemEvent.AddItemToCartEvent)
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.restaurantMenuItemStateFlow.collect {
                        handleState(it)
                    }
                }

                launch {
                    viewModel.uiEvent.collect {
                        handleUiEvent(it)
                    }
                }
            }
        }
    }

    private fun handleUiEvent(event: RestaurantMenuItemViewModel.RestaurantMenuItemUiEvent) {
        when(event) {
            is RestaurantMenuItemViewModel.RestaurantMenuItemUiEvent.DismissBottomSheetEvent -> dismiss()
        }
    }

    private fun setUpRecycler() = with(binding.recyclerOptions) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = restaurantMenuAdapter
        restaurantMenuAdapter.onOptionClick = {
            viewModel.onEvent(RestaurantMenuItemEvent.UpdateRestaurantDetailsEvent(it))
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

            binding.tvQuantity.text = quantity.toString()

            totalPrice?.let {
                binding.tvTotalPrice.text = String.format("%.2f ₾", it)
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

    private fun setUpQuantityPlusMinus(){
        binding.imageButtonMinus.setOnClickListener {
            viewModel.onEvent(RestaurantMenuItemEvent.MinusQuantityEvent(binding.tvQuantity.text.toString().toInt()))
        }

        binding.imageButtonPlus.setOnClickListener {
            viewModel.onEvent(RestaurantMenuItemEvent.PlusQuantityEvent(binding.tvQuantity.text.toString().toInt()))
        }
    }

    private fun submitMenuItemData(menuOrderDetails: MenuOrderDetails) {
        viewModel.onEvent(RestaurantMenuItemEvent.SetRestaurantDetailsEvent(menuOrderDetails))
    }

    companion object {
        const val RESTAURANT_MENU_BOTTOM_SHEET = "RestaurantMenuBottomSheet"
    }
}