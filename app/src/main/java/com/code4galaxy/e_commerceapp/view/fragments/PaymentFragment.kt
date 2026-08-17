package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.databinding.FragmentPaymentBinding
import com.code4galaxy.e_commerceapp.viewModel.CheckoutViewModel

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding

    private var selectedPaymentMethod: String? = null

    private val checkoutViewModel: CheckoutViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPaymentBinding.inflate(
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

        setUpPaymentSelection()
        setUpNextButton()
    }

    private fun setUpPaymentSelection() {

        binding.radioCash.setOnClickListener {
            selectCashOnDelivery()
        }

        binding.tvCash.setOnClickListener {
            selectCashOnDelivery()
        }

        binding.radioInternetBanking.setOnClickListener {
            showNotAvailable()
        }

        binding.radioCard.setOnClickListener {
            showNotAvailable()
        }

        binding.radioPayPal.setOnClickListener {
            showNotAvailable()
        }
    }

    private fun selectCashOnDelivery() {

        selectedPaymentMethod = "COD"

        checkoutViewModel.setPaymentMethod("COD")

        Log.d(
            "CHECKOUT_TEST",
            "Payment saved = ${checkoutViewModel.paymentMethod}"
        )

        binding.radioCash.isChecked = true

        binding.radioInternetBanking.isChecked = false
        binding.radioCard.isChecked = false
        binding.radioPayPal.isChecked = false
    }

    private fun showNotAvailable() {

        Toast.makeText(
            requireContext(),
            "This payment method is not available yet",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setUpNextButton() {

        binding.btnNext.setOnClickListener {

            if (selectedPaymentMethod == null) {

                Toast.makeText(
                    requireContext(),
                    "Please select payment method",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val viewPager =
                requireParentFragment()
                    .requireView()
                    .findViewById<ViewPager2>(
                        R.id.viewPager
                    )

            viewPager.currentItem = 3
        }
    }
}