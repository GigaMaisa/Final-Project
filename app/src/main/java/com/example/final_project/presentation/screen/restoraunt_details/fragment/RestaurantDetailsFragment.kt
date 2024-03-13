package com.example.final_project.presentation.screen.restoraunt_details.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.R
import com.example.final_project.databinding.FragmentRestorauntDetailsBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.RestaurantDetailsEvent
import com.example.final_project.presentation.extension.showSnackBar
import com.example.final_project.presentation.model.restaurant.RestaurantDetails
import com.example.final_project.presentation.screen.restoraunt_details.adapter.RestaurantMenuRecyclerViewAdapter
import com.example.final_project.presentation.screen.restoraunt_details.viewmodel.RestaurantDetailsViewModel
import com.example.final_project.presentation.state.RestaurantDetailsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RestaurantDetailsFragment : BaseFragment<FragmentRestorauntDetailsBinding>(FragmentRestorauntDetailsBinding::inflate) {

    private val viewModel: RestaurantDetailsViewModel by viewModels()
    private val restaurantMenuRecyclerAdapter = RestaurantMenuRecyclerViewAdapter()
    private val args: RestaurantDetailsFragmentArgs by navArgs()

    override fun setUp() {
        setUpBottomSheet()
        setUpRecycler()
        viewModel.onEvent(RestaurantDetailsEvent.GetRestaurantDetailsEvent(args.restaurantId))
    }

    override fun setUpListeners() {
        restaurantMenuRecyclerAdapter.onClick = {

        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.restaurantMenuStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: RestaurantDetailsState) = with(state) {
        if (isFavourite) {
            binding.imageBtnHeart.setImageResource(R.drawable.ic_heart)
        }else {
            binding.imageBtnHeart.setImageResource(R.drawable.ic_heart_ourline)
        }

        restaurantDetails?.let {
            setUpRestaurantDetails(it)
        }

        errorMessage?.let {
            requireView().showSnackBar(resources.getString(it))
            viewModel.onEvent(RestaurantDetailsEvent.UpdateErrorMessageEvent(null))
        }
    }

    private fun setUpRecycler() {
        with(binding.recyclerViewMenu) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = restaurantMenuRecyclerAdapter
        }
    }

    private fun setUpRestaurantDetails(restaurantDetails: RestaurantDetails) = with(binding) {
        tvTitle.text = restaurantDetails.restaurantName
        tvType.text = restaurantDetails.type
        tvRating.text = restaurantDetails.rating.toString()
        tvDescription.text = restaurantDetails.description
        restaurantDetails.deliveryFee?.let {
            tvDeliveryFee.text = it.toString().plus(" ₾")
        }
        restaurantMenuRecyclerAdapter.submitList(restaurantDetails.menu)
    }

    private fun setUpBottomSheet() {
//        findNavController().navigate(RestaurantDetailsFragmentDirections.actionRestaurantDetailsFragmentToRestaurantBottomSheet())
    }
}