package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.code4galaxy.e_commerceapp.database.CartDatabase
import com.code4galaxy.e_commerceapp.databinding.FragmentCartBinding
import com.code4galaxy.e_commerceapp.repository.CartRepositoryImpl
import com.code4galaxy.e_commerceapp.utils.SessionManager
import com.code4galaxy.e_commerceapp.utils.UIState
import com.code4galaxy.e_commerceapp.view.adapters.CartAdapter
import com.code4galaxy.e_commerceapp.viewModel.CartViewModel
import com.code4galaxy.e_commerceapp.viewModel.CartViewModelFactory

class CartFragment: Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var userId: String



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCartBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = SessionManager(requireContext()).getUserId()

        setUpRecyclerView()
        setUpCartViewModel()
        setUpCartObserver()

        cartViewModel.getCartItems(userId)
    }

    private fun setUpCartObserver() {
        cartViewModel.cartState.observe(viewLifecycleOwner){state ->

           when(state){
               is UIState.Loading ->{
                   //progress bar later
               }

               is UIState.Success -> {
                   val cartItems = state.data

                   if (cartItems.isEmpty()){
                       with(binding){
                           tvEmptyCart.visibility = View.VISIBLE
                           rvCart.visibility = View.GONE
                           btnCheckout.visibility = View.GONE
                           tvTotalBillLabel.text = "$ 0"
                       }
                   } else{
                       with(binding){

                           tvEmptyCart.visibility = View.GONE
                           rvCart.visibility = View.VISIBLE
                           btnCheckout.visibility = View.VISIBLE
                       }

                       binding.rvCart.adapter = CartAdapter(cartItems,
                           onPlusClick = {cartItems ->
                               cartViewModel.increaseQuantity(
                                   userId, cartItems.productId, cartItems.quantity
                               )
                           },
                           onMinusClick = { cartItems ->
                               cartViewModel.decreaseQuantity(userId,cartItems.productId, cartItems.quantity)
                           })

                       val totalBill = cartItems.sumOf { cartItems ->
                           val price = cartItems.unitPrice.toDoubleOrNull() ?: 0.0

                           price * cartItems.quantity
                       }

                       binding.tvTotalBill.text = "$ $totalBill"
                   }

               }

               is UIState.Error -> {
                   Toast.makeText(requireContext(),state.message, Toast.LENGTH_LONG).show()
               }
           }
        }
    }

    private fun setUpCartViewModel() {
        val cartDao = CartDatabase.getDatabase(requireContext())
            .cartDao()

        val repository = CartRepositoryImpl(cartDao)

        val factory = CartViewModelFactory(repository)

        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]
    }

    private fun setUpRecyclerView() {
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
    }
}