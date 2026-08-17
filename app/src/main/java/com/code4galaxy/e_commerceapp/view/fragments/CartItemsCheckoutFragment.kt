package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.database.CartDatabase
import com.code4galaxy.e_commerceapp.database.CartEntity
import com.code4galaxy.e_commerceapp.databinding.FragmentCartItemsCheckoutBinding
import com.code4galaxy.e_commerceapp.repository.CartRepositoryImpl
import com.code4galaxy.e_commerceapp.utils.SessionManager
import com.code4galaxy.e_commerceapp.utils.UIState
import com.code4galaxy.e_commerceapp.view.adapters.CheckoutCartAdapter
import com.code4galaxy.e_commerceapp.viewModel.CartViewModel
import com.code4galaxy.e_commerceapp.viewModel.CartViewModelFactory
import com.code4galaxy.e_commerceapp.viewModel.CheckoutViewModel

class CartItemsCheckoutFragment : Fragment() {

    private lateinit var binding:
            FragmentCartItemsCheckoutBinding

    private lateinit var cartViewModel: CartViewModel

    private lateinit var userId: String

    private val checkoutViewModel: CheckoutViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            FragmentCartItemsCheckoutBinding.inflate(
                inflater,
                container,
                false
            )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        userId =
            SessionManager(requireContext()).getUserId()

        setUpRecyclerView()
        setUpCartViewModel()
        setUpObserver()

        cartViewModel.getCartItems(userId)
    }

    private fun setUpRecyclerView() {

        binding.rvCheckoutCart.layoutManager =
            LinearLayoutManager(requireContext())
    }

    private fun setUpCartViewModel() {

        val cartDao =
            CartDatabase
                .getDatabase(requireContext())
                .cartDao()

        val repository =
            CartRepositoryImpl(cartDao)

        val factory =
            CartViewModelFactory(repository)

        cartViewModel =
            ViewModelProvider(
                this,
                factory
            )[CartViewModel::class.java]
    }

    private fun setUpObserver() {

        cartViewModel.cartState.observe(
            viewLifecycleOwner
        ) { state ->

            when (state) {

                is UIState.Success -> {

                    val cartItems = state.data

                    checkoutViewModel.cartItems = cartItems

                    binding.rvCheckoutCart.adapter =
                        CheckoutCartAdapter(cartItems)

                    calculateTotal(cartItems)

                    binding.btnNext.setOnClickListener {

                        val viewPager =
                            requireParentFragment()
                                .requireView()
                                .findViewById<ViewPager2>(R.id.viewPager)

                        viewPager.currentItem = 1
                    }
                }

                is UIState.Error -> {

                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {}
            }
        }
    }

    private fun calculateTotal(
        cartItems: List<CartEntity>
    ) {

        var total = 0.0

        for (item in cartItems) {

            total +=
                item.unitPrice.toDouble() *
                        item.quantity
        }

        checkoutViewModel.billAmount = total

        binding.tvTotalAmount.text =
            "$ $total"
    }
}