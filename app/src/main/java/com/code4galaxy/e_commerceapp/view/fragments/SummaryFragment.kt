package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.database.CartDatabase
import com.code4galaxy.e_commerceapp.databinding.FragmentSummaryBinding
import com.code4galaxy.e_commerceapp.model.OrderDeliveryAddress
import com.code4galaxy.e_commerceapp.model.OrderItem
import com.code4galaxy.e_commerceapp.model.OrderRequest
import com.code4galaxy.e_commerceapp.network.RetrofitClient
import com.code4galaxy.e_commerceapp.repository.CartRepositoryImpl
import com.code4galaxy.e_commerceapp.repository.OrderRepositoryImpl
import com.code4galaxy.e_commerceapp.utils.SessionManager
import com.code4galaxy.e_commerceapp.utils.UIState
import com.code4galaxy.e_commerceapp.view.adapters.CheckoutCartAdapter
import com.code4galaxy.e_commerceapp.viewModel.CartViewModel
import com.code4galaxy.e_commerceapp.viewModel.CartViewModelFactory
import com.code4galaxy.e_commerceapp.viewModel.CheckoutViewModel
import com.code4galaxy.e_commerceapp.viewModel.OrderViewModel
import com.code4galaxy.e_commerceapp.viewModel.OrderViewModelFactory

class SummaryFragment : Fragment() {

    private lateinit var binding: FragmentSummaryBinding

    private val checkoutViewModel: CheckoutViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    private lateinit var orderViewModel: OrderViewModel

    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSummaryBinding.inflate(
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

        setUpRecyclerView()

        setUpObserver()

        setUpOrderViewModel()

        setUpOrderObserver()

        setUpCartViewModel()

        binding.tvTotalAmount.text =
            "$ ${checkoutViewModel.billAmount}"

        binding.btnPlaceOrder.setOnClickListener {
            placeOrder()
        }
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

    private fun setUpRecyclerView() {

        binding.rvCartItems.layoutManager =
            LinearLayoutManager(requireContext())

        binding.rvCartItems.adapter =
            CheckoutCartAdapter(
                checkoutViewModel.cartItems
            )
    }

    private fun setUpObserver() {

        checkoutViewModel.selectedAddress.observe(
            viewLifecycleOwner
        ) { address ->

            binding.tvAddressTitle.text =
                address?.title ?: ""

            binding.tvAddress.text =
                address?.address ?: ""
        }

        checkoutViewModel.paymentMethod.observe(
            viewLifecycleOwner
        ) { paymentMethod ->

            binding.tvPaymentMethod.text =
                if (paymentMethod == "COD") {
                    "Cash On Delivery"
                } else {
                    ""
                }
        }
    }

    private fun setUpOrderViewModel() {

        val repository =
            OrderRepositoryImpl(
                RetrofitClient.apiServices
            )

        val factory =
            OrderViewModelFactory(repository)

        orderViewModel =
            ViewModelProvider(
                this,
                factory
            )[OrderViewModel::class.java]

    }

    private fun placeOrder() {

        if (checkoutViewModel.cartItems.isEmpty()) {

            Toast.makeText(
                requireContext(),
                "Cart is empty",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val address =
            checkoutViewModel.selectedAddress.value

        val paymentMethod =
            checkoutViewModel.paymentMethod.value

        if (address == null || paymentMethod == null) {

            Toast.makeText(
                requireContext(),
                "Checkout information is missing",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val userId =
            SessionManager(requireContext())
                .getUserId()

        val orderItems =
            checkoutViewModel.cartItems.map { cartItem ->

                OrderItem(
                    product_id = cartItem.productId,
                    quantity = cartItem.quantity,
                    unit_price = cartItem.unitPrice.toDouble()
                )
            }

        val request =
            OrderRequest(
                user_id = userId,

                delivery_address =
                    OrderDeliveryAddress(
                        title = address.title,
                        address = address.address
                    ),

                items = orderItems,

                bill_amount =
                    checkoutViewModel.billAmount,

                payment_method =
                    paymentMethod
            )

        orderViewModel.placeOrder(request)

    }

    private fun setUpOrderObserver() {

        orderViewModel.orderState.observe(
            viewLifecycleOwner
        ) { state ->

            when (state) {

                is UIState.Loading -> {

                    binding.btnPlaceOrder.isEnabled = false
                }

                is UIState.Success -> {

                    binding.btnPlaceOrder.isEnabled = true

                    val userId =
                        SessionManager(requireContext())
                            .getUserId()

                    cartViewModel.clearCart(userId)

                    val orderId =
                        state.data.order_id ?: return@observe

                    val bundle = Bundle().apply {
                        putInt("orderId", orderId)
                    }

                    findNavController().navigate(
                        R.id.orderConfirmedFragment,
                        bundle
                    )
                }

                is UIState.Error -> {

                    binding.btnPlaceOrder.isEnabled = true

                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}