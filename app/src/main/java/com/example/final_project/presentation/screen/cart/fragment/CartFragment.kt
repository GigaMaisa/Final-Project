package com.example.final_project.presentation.screen.cart.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.NavGraphDirections
import com.example.final_project.R
import com.example.final_project.databinding.FragmentCartBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.CartEvent
import com.example.final_project.presentation.model.cart.CartCheckout
import com.example.final_project.presentation.screen.cart.adapter.CartRecyclerViewAdapter
import com.example.final_project.presentation.screen.cart.viewmodel.CartViewModel
import com.example.final_project.presentation.state.CartState
import com.example.final_project.presentation.util.CartItemTouchHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate), CartItemTouchHelper.RecyclerItemTouchHelperListener {
    private val cartAdapter = CartRecyclerViewAdapter()
    private val viewModel: CartViewModel by viewModels()

    override fun setUp() {
        viewModel.onEvent(CartEvent.GetAllOrders)
        initRecycler()
    }

    override fun setUpListeners() {
        setUpCartAddRemoveListener()
        setUpSubmitOrderListener()
        binding.btnGoBack.setOnClickListener {
            viewModel.onEvent(CartEvent.GoBackEvent)
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.cartStateFlow.collect {
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

    private fun initRecycler() = with(binding.recyclerViewCart) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = cartAdapter
        val itemTouchHelperCallback = CartItemTouchHelper(requireContext(), this@CartFragment)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerViewCart)
    }

    private fun handleState(state: CartState) {
        state.cartItems?.let { cartItems ->
            state.checkout?.let {
                if (state.cartItems.isEmpty()) {
                    binding.recyclerViewCart.visibility = View.GONE
                    binding.tvNoItemsAlert.visibility = View.VISIBLE
                } else {
                    val listOfCartCheckout = mutableListOf<CartCheckout>()

                    cartItems.forEach {
                        listOfCartCheckout.add(CartCheckout(id = it.foodId, cartItem = it))
                    }

                    listOfCartCheckout.add(CartCheckout("9", null, it))
                    cartAdapter.submitList(listOfCartCheckout)
                }
            }
        }
    }

    private fun handleUiEvent(event: CartViewModel.CartUiEvent)  {
        when(event) {
            is CartViewModel.CartUiEvent.GoBackEvent -> findNavController().navigateUp()
            is CartViewModel.CartUiEvent.GoToDeliveryMapEvent -> requireActivity().findNavController(
                R.id.nav_host_fragment).navigate(NavGraphDirections.actionGlobalToCourierDeliveryFragment(location = event.latLng))
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        viewModel.onEvent(CartEvent.DeleteItemEvent(cartAdapter.currentList[position].id))
    }

    private fun setUpCartAddRemoveListener() {
        with(cartAdapter) {
            onMinusClick = {
                viewModel.onEvent(CartEvent.RemoveCartItemQuantityEvent(it))
            }

            onPLusClick = {
                viewModel.onEvent(CartEvent.AddCartItemQuantityEvent(it))
            }
        }
    }

    private fun setUpSubmitOrderListener() {
        cartAdapter.onPlaceMyOrderClick = {
            viewModel.onEvent(CartEvent.SubmitOrderEvent)
        }
    }

}