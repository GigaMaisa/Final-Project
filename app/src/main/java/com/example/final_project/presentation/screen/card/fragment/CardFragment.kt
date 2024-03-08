package com.example.final_project.presentation.screen.card.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.databinding.FragmentCardBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.CardNavigationEvents
import com.example.final_project.presentation.model.card.Card
import com.example.final_project.presentation.screen.card.adapter.CardsRecyclerAdapter
import com.example.final_project.presentation.screen.card.viewmodel.CardViewModel
import com.example.final_project.presentation.state.CardsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardFragment : BaseFragment<FragmentCardBinding>(FragmentCardBinding::inflate) {
    private val viewModel: CardViewModel by viewModels()
    private val cardsAdapter by lazy { CardsRecyclerAdapter() }
    
    override fun setUp() {
        setUpRecycler()
        viewModel.onEvent(CardViewModel.CardsEvents.GetCardsFromDb)
    }

    override fun setUpListeners() = with(binding) {
        btnGoBack.setOnClickListener {
            viewModel.onUiEvent(CardNavigationEvents.NavigateBack)
        }

        fabAddCard.setOnClickListener {
            viewModel.onUiEvent(CardNavigationEvents.NavigateToAddNewCard)
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiEvent.collect {
                        handleNavigationEvents(event = it)
                    }
                }

                launch {
                    viewModel.cardsStateFlow.collect {
                        handleCardsState(state = it)
                    }
                }
            }
        }
    }

    private fun handleNavigationEvents(event: CardNavigationEvents) {
        when (event) {
            is CardNavigationEvents.NavigateBack -> findNavController().navigateUp()
            is CardNavigationEvents.NavigateToAddNewCard -> findNavController().navigate(CardFragmentDirections.actionCardFragmentToAddCardFragment())
        }
    }
    
    private fun setUpRecycler() = with(binding) {
        rvCards.apply { 
            adapter = cardsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun handleCardsState(state: CardsState) = with(binding) {
        state.cards?.let {
            if (it.isEmpty()) {
                rvCards.visibility = View.GONE
                tvNoItemsAlert.visibility = View.VISIBLE
            } else {
                rvCards.visibility = View.VISIBLE
                tvNoItemsAlert.visibility = View.GONE

                cardsAdapter.submitList(it)
            }
        }
    }
}