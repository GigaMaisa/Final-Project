package com.example.final_project.presentation.screen.restoraunt_details.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.final_project.R
import com.example.final_project.databinding.RestaurantDetailsBottomSheetDialogBinding
import com.example.final_project.presentation.event.RestaurantDetailsEvent
import com.example.final_project.presentation.screen.restoraunt_details.adapter.RestaurantMenuRecyclerViewAdapter
import com.example.final_project.presentation.screen.restoraunt_details.viewmodel.RestaurantDetailsViewModel
import com.example.final_project.presentation.state.RestaurantDetailsState
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class RestaurantBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: RestaurantDetailsBottomSheetDialogBinding
    private val restaurantMenuAdapter = RestaurantMenuRecyclerViewAdapter()
    private val viewModel: RestaurantDetailsViewModel by viewModels()
    private var favourite by Delegates.notNull<Boolean>()

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
        setUpObservers()
        setUpBottomSheetBehavior()
        viewModel.onEvent(RestaurantDetailsEvent.GetIfFavouriteEvent(restaurantId = 0))
    }

    private fun setUpRecycler() = with(binding.recyclerViewMenu) {
        layoutManager = GridLayoutManager(context, 2)
        adapter = restaurantMenuAdapter
    }

    private fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.restaurantMenuStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: RestaurantDetailsState){
        with(state) {


            favourite = isFavourite
            if (isFavourite) {
                binding.imageBtnHeart.setImageResource(R.drawable.ic_heart)
            }else {
                binding.imageBtnHeart.setImageResource(R.drawable.ic_heart_ourline)
            }
        }
    }

    private fun setUpBottomSheetBehavior() {
        val bottomSheetBehavior = BottomSheetBehavior.from(view?.parent as View)
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
}