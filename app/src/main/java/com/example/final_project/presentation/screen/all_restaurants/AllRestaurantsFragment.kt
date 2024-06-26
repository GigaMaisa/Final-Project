package com.example.final_project.presentation.screen.all_restaurants

import android.util.Log.d
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.databinding.FragmentAllRestaurantsBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.restaurant.AllRestaurantsEvent
import com.example.final_project.presentation.extension.showSnackBar
import com.example.final_project.presentation.model.restaurant.RestaurantType
import com.example.final_project.presentation.screen.all_restaurants.adapter.AllRestaurantsRecyclerViewAdapter
import com.example.final_project.presentation.state.AllRestaurantsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllRestaurantsFragment : BaseFragment<FragmentAllRestaurantsBinding>(FragmentAllRestaurantsBinding::inflate) {

    private val viewModel: AllRestaurantsViewModel by viewModels()
    private val args: AllRestaurantsFragmentArgs by navArgs()
    private val allRestaurantsRecyclerAdapter = AllRestaurantsRecyclerViewAdapter()

    override fun setUp() {
        setUpRecycler()
        d("showArgsMate", "${args.restaurantType}  ${args.searchFilter}")
        when(args.restaurantType) {
            RestaurantType.ALL -> viewModel.onEvent(AllRestaurantsEvent.GetAllRestaurantsEvent(args.searchFilter))
            RestaurantType.FAVOURITES -> viewModel.onEvent(AllRestaurantsEvent.GetFavouriteRestaurants)
            else -> viewModel.onEvent(AllRestaurantsEvent.GetRestaurantByCategoryEvent(args.restaurantType))
        }
    }

    override fun setUpListeners() {
        allRestaurantsRecyclerAdapter.onClick = {
            viewModel.onEvent(AllRestaurantsEvent.GoToRestaurantsDetailsEvent(restaurantId = it))
        }

        binding.btnGoBack.setOnClickListener {
            viewModel.onEvent(AllRestaurantsEvent.GoBackEvent)
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.restaurantsStateFlow.collect {
                        handleState(it)
                    }
                }

                launch {
                    viewModel.uiState.collect {
                        handleUiEvent(it)
                    }
                }
            }
        }
    }

    private fun handleState(state: AllRestaurantsState) = with(state) {
        restaurants?.let {
            allRestaurantsRecyclerAdapter.submitList(it)
        }

        binding.progressBar.isVisible = isLoading

        errorMessage?.let {
            requireView().showSnackBar(resources.getString(it))
            viewModel.onEvent(AllRestaurantsEvent.UpdateErrorMessageEvent(null))
        }
    }

    private fun handleUiEvent(event: AllRestaurantsViewModel.AllRestaurantsUiEvents) {
        when(event) {
            is AllRestaurantsViewModel.AllRestaurantsUiEvents.NavigateBack -> findNavController().navigateUp()
            is AllRestaurantsViewModel.AllRestaurantsUiEvents.NavigateToRestaurantDetails ->
                findNavController().navigate(AllRestaurantsFragmentDirections.actionAllRestaurantsFragmentToRestaurantDetailsFragment(
                restaurantId = event.restaurantId
            ))
        }
    }

    private fun setUpRecycler() {
        with(binding.recyclerRestaurants) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = allRestaurantsRecyclerAdapter
        }
    }

}