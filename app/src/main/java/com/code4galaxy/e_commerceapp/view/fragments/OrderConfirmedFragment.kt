package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.code4galaxy.e_commerceapp.databinding.FragmentOrderConfirmedBinding
import com.code4galaxy.e_commerceapp.network.RetrofitClient
import com.code4galaxy.e_commerceapp.repository.OrderRepositoryImpl
import com.code4galaxy.e_commerceapp.utils.UIState
import com.code4galaxy.e_commerceapp.view.adapters.OrderDetailsAdapter
import com.code4galaxy.e_commerceapp.viewModel.OrderViewModel
import com.code4galaxy.e_commerceapp.viewModel.OrderViewModelFactory

class OrderConfirmedFragment : Fragment() {

    private lateinit var binding: FragmentOrderConfirmedBinding

    private lateinit var orderViewModel: OrderViewModel

    private var orderId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentOrderConfirmedBinding.inflate(
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

        orderId =
            arguments?.getInt("orderId") ?: 0


        setUpViewModel()


        binding.rvPurchaseItems.layoutManager =
            LinearLayoutManager(requireContext())


        setUpObserver()

        if (orderId != 0) {

            orderViewModel.getOrderDetails(orderId)

        } else {

            Toast.makeText(
                requireContext(),
                "Invalid Order ID",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnCancelOrder.setOnClickListener {

            Toast.makeText(
                requireContext(),
                "Order Canceled",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setUpViewModel() {

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

    private fun setUpObserver() {

        orderViewModel.orderDetailsState.observe(
            viewLifecycleOwner
        ) { state ->

            when (state) {

                is UIState.Loading -> {

                    // Progress bar later
                }

                is UIState.Success -> {

                    val order =
                        state.data.order


                    binding.tvOrderId.text =
                        "#${order.order_id}"


                    binding.tvOrderStatus.text =
                        order.order_status


                    binding.tvTotalAmount.text =
                        "$ ${order.bill_amount}"


                    binding.tvAddressTitle.text =
                        order.address_title


                    binding.tvAddress.text =
                        order.address


                    binding.tvPaymentMethod.text =
                        if (order.payment_method == "COD") {
                            "Cash On Delivery"
                        } else {
                            order.payment_method
                        }


                    binding.rvPurchaseItems.adapter =
                        OrderDetailsAdapter(
                            order.items
                        )
                }

                is UIState.Error -> {

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