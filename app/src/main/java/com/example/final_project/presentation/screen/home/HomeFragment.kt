package com.example.final_project.presentation.screen.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.final_project.databinding.FragmentHomeBinding
import com.example.final_project.presentation.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val offerAdapter = OffersViewPagerAdapter()
    override fun setUp() {
        with(binding) {
            viewPagerPopular.adapter = offerAdapter
            TabLayoutMediator(intoTabLayout, viewPagerPopular) { tab, position -> }.attach()
        }
    }

    override fun setUpListeners() {

    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.offerStateFlow.collectLatest {
                    offerAdapter.submitList(it)
                }
            }
        }
    }
}