package com.example.final_project.presentation.screen.cart.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.FragmentCartBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.CartEvent
import com.example.final_project.presentation.model.cart.CartCheckout
import com.example.final_project.presentation.screen.cart.viewmodel.CartViewModel
import com.example.final_project.presentation.screen.cart.adapter.CartRecyclerViewAdapter
import com.example.final_project.presentation.state.CartState
import com.example.final_project.presentation.util.CartItemTouchHelper
import kotlinx.coroutines.launch

class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate), CartItemTouchHelper.RecyclerItemTouchHelperListener {
    private val cartAdapter = CartRecyclerViewAdapter()
    private val viewModel: CartViewModel by viewModels()
    override fun setUp() {
        with(binding.recyclerViewCart) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
            val itemTouchHelperCallback = CartItemTouchHelper(requireContext(), this@CartFragment)
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerViewCart)

        }
    }

    override fun setUpListeners() {
        setUpCartAddRemoveListener()
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cartStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: CartState) {
        state.cartItems?.let {cartItems ->
            state.checkout?.let {
                val listOfCartCheckout = mutableListOf<CartCheckout>()
                cartItems.forEach {
                    listOfCartCheckout.add(CartCheckout(id = it.id, cartItem = it))
                }
                listOfCartCheckout.add(CartCheckout(it.id, null, it))
                cartAdapter.submitList(listOfCartCheckout)
            }

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

}