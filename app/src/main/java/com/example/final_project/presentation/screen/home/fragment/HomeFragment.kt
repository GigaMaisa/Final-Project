package com.example.final_project.presentation.screen.home.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.R
import com.example.final_project.databinding.FragmentHomeBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.home.HomeEvent
import com.example.final_project.presentation.extension.showSnackBar
import com.example.final_project.presentation.model.restaurant.RestaurantType
import com.example.final_project.presentation.screen.bottom_nav_container.BottomNavContainerFragmentDirections
import com.example.final_project.presentation.screen.home.viewmodel.HomeViewModel
import com.example.final_project.presentation.screen.home.adapter.BannersViewPagerAdapter
import com.example.final_project.presentation.screen.home.adapter.CategoriesRecyclerAdapter
import com.example.final_project.presentation.screen.home.adapter.RestaurantsRecyclerAdapter
import com.example.final_project.presentation.state.HomeState
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val bannerAdapter = BannersViewPagerAdapter()
    private val restaurantsRecyclerAdapter by lazy { RestaurantsRecyclerAdapter() }
    private val favouriteRestaurantsAdapter by lazy { RestaurantsRecyclerAdapter() }
    private val categoriesAdapter by lazy { CategoriesRecyclerAdapter() }

    override fun setUp() {
        viewModel.onEvent(HomeEvent.GetBannersEvent)
        viewModel.onEvent(HomeEvent.GetRestaurantsEvent)
        viewModel.onEvent(HomeEvent.GetFavouriteRestaurantsEvent)
        viewModel.onEvent(HomeEvent.GetCategoriesEvent)

        setUpViewPager()
        setUpRestaurantsRecyclerView()
        setUpFavouriteRestaurantsRecycler()
        setUpCategoriesRecycler()
    }

    override fun setUpListeners() = with(binding)  {
        restaurantsRecyclerAdapter.onClick = {
            viewModel.onEvent(HomeEvent.GoToRestaurantDetailsEvent(it))
        }

        btnSeeAllRestaurants.setOnClickListener {
            viewModel.onEvent(HomeEvent.GoToAllRestaurantsEvent())
        }

        btnSeeAllFavouriteRestaurants.setOnClickListener {
            viewModel.onEvent(HomeEvent.GoToAllRestaurantsEvent(restaurantType = RestaurantType.FAVOURITES))
        }

        favouriteRestaurantsAdapter.onClick = {
            viewModel.onEvent(HomeEvent.GoToRestaurantDetailsEvent(it))
        }

        categoriesAdapter.onClick = {
            viewModel.onEvent(HomeEvent.GoToAllRestaurantsEvent(restaurantType = it))
        }

        imageBtnFilter.setOnClickListener {
            viewModel.onEvent(HomeEvent.GoToAllRestaurantsEvent(searchFilter = etSearch.text.toString()))
            etSearch.setText("")
        }

        bannerAdapter.onBuyClick = {
            viewModel.onEvent(HomeEvent.GoToRestaurantDetailsEvent(it))
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.homeStateFlow.collect {
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

    private fun handleUiEvent(event: HomeViewModel.HomeUiEvent) {
        when(event) {
            is HomeViewModel.HomeUiEvent.GoToAllRestaurantsFragment ->
                requireActivity().findNavController(R.id.nested_nav_host_fragment).navigate(BottomNavContainerFragmentDirections
                    .actionBottomNavPlaceHolderToAllRestaurantsFragment(restaurantType = event.restaurantType, searchFilter = event.searchFilter))

            is HomeViewModel.HomeUiEvent.GoToRestaurantDetailsFragment ->
                requireActivity().findNavController(R.id.nested_nav_host_fragment).navigate(BottomNavContainerFragmentDirections
                    .actionBottomNavPlaceHolderToRestaurantDetailsFragment(restaurantId = event.restaurantId))


        }
    }

    private fun handleState(state: HomeState) = with(state) {
        banners?.let {
            bannerAdapter.submitList(it)
        }

        restaurants?.let {
            restaurantsRecyclerAdapter.submitList(it)
        }

        errorMessage?.let {
            requireView().showSnackBar(resources.getString(it))
        }

        favouriteRestaurants?.let {
            favouriteRestaurantsAdapter.submitList(it)
        }

        categories?.let {
            categoriesAdapter.submitList(it)
        }
    }

    private fun setUpRestaurantsRecyclerView() = with(binding) {
        rvRestaurants.apply {
            adapter = restaurantsRecyclerAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpFavouriteRestaurantsRecycler() = with(binding.recyclerViewFavouriteRestaurants) {
        adapter = favouriteRestaurantsAdapter
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpCategoriesRecycler() = with(binding.rvCategories) {
        adapter = categoriesAdapter
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpViewPager() {
        with(binding) {
            viewPagerPopular.adapter = bannerAdapter
            TabLayoutMediator(intoTabLayout, viewPagerPopular) { tab, position -> }.attach()
        }
    }
}