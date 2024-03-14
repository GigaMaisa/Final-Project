package com.example.final_project.presentation.screen.cart.fragment

import android.util.Log
import android.view.View
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
import com.example.final_project.presentation.model.order.Order
import com.example.final_project.presentation.screen.cart.viewmodel.CartViewModel
import com.example.final_project.presentation.screen.cart.adapter.CartRecyclerViewAdapter
import com.example.final_project.presentation.state.CartState
import com.example.final_project.presentation.util.CartItemTouchHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate), CartItemTouchHelper.RecyclerItemTouchHelperListener {
    private val cartAdapter = CartRecyclerViewAdapter()
    private val viewModel: CartViewModel by viewModels()
    override fun setUp() {
//        viewModel.onEvent(CartEvent.AddOrder(
//            order = Order(
//                foodId = "asadsas",
//                restaurantId = 0,
//                name = "Big Mac",
//                image = "https://imageproxy.wolt.com/menu/menu-images/63327ccdaf6b62533484dfbf/b9ed0e0e-f007-11ed-bdb4-42bd159e3e10_wolt_8162.png?w=400",
//                menuCategory = "MacMenu",
//                quantity = 3,
//                price = 30.6
//            )
//        ))

        viewModel.onEvent(CartEvent.GetAllOrders)
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