package com.example.final_project.presentation.screen.home.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.databinding.FragmentHomeBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.home.HomeEvent
import com.example.final_project.presentation.extension.showSnackBar
import com.example.final_project.presentation.screen.home.viewmodel.HomeViewModel
import com.example.final_project.presentation.screen.home.adapter.BannersViewPagerAdapter
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

    override fun setUp() {
        viewModel.onEvent(HomeEvent.GetBannersEvent)
        viewModel.onEvent(HomeEvent.GetRestaurantsEvent)

        setUpViewPager()
        setUpRestaurantsRecyclerView()
    }

    override fun setUpListeners()  {
        restaurantsRecyclerAdapter.onClick = {
            // navigate to restaurant menu
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: HomeState) = with(state) {
        banners?.let {
            bannerAdapter.submitList(it)
        }

        restaurants?.let {
            Log.d("RESTORNEBI SMN", it.joinToString())
            restaurantsRecyclerAdapter.submitList(it)
        }

        errorMessage?.let {
            requireView().showSnackBar(resources.getString(it))
        }
    }

    private fun setUpRestaurantsRecyclerView() = with(binding) {
        rvRestaurants.apply {
            adapter = restaurantsRecyclerAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setUpViewPager() {
        with(binding) {
            viewPagerPopular.adapter = bannerAdapter
            TabLayoutMediator(intoTabLayout, viewPagerPopular) { tab, position -> }.attach()
        }
    }
}