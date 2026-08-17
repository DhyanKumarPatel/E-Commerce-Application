package com.code4galaxy.e_commerceapp.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
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
import com.code4galaxy.e_commerceapp.databinding.DialogAddAddressBinding
import com.code4galaxy.e_commerceapp.databinding.FragmentDelievryBinding
import com.code4galaxy.e_commerceapp.model.AddAddressRequest
import com.code4galaxy.e_commerceapp.model.Address
import com.code4galaxy.e_commerceapp.network.RetrofitClient
import com.code4galaxy.e_commerceapp.repository.AddressRepositoryImpl
import com.code4galaxy.e_commerceapp.utils.SessionManager
import com.code4galaxy.e_commerceapp.utils.UIState
import com.code4galaxy.e_commerceapp.view.adapters.AddressAdapter
import com.code4galaxy.e_commerceapp.viewModel.AddressViewModel
import com.code4galaxy.e_commerceapp.viewModel.AddressViewModelFactory
import com.code4galaxy.e_commerceapp.viewModel.CheckoutViewModel

class DeliveryFragment : Fragment() {

    private lateinit var binding: FragmentDelievryBinding

    private lateinit var addressViewModel: AddressViewModel

    private lateinit var userId: String

    private var selectedAddress: Address? = null

    private val checkoutViewModel: CheckoutViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDelievryBinding.inflate(
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
        setUpViewModel()
        setUpObserver()
        setUpAddAddressObserver()

        binding.btnAddAddress.setOnClickListener {
            showAddAddressDialog()
        }

        addressViewModel.getAddresses(userId)

        binding.btnNext.setOnClickListener {

            if (selectedAddress == null) {

                Toast.makeText(
                    requireContext(),
                    "Please select a delivery address",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val viewPager =
                requireParentFragment()
                    .requireView()
                    .findViewById<ViewPager2>(R.id.viewPager)

            viewPager.currentItem = 2
        }
    }

    private fun setUpRecyclerView() {

        binding.rvAddresses.layoutManager =
            LinearLayoutManager(requireContext())
    }

    private fun setUpViewModel() {

        val repository =
            AddressRepositoryImpl(
                RetrofitClient.apiServices
            )

        val factory =
            AddressViewModelFactory(repository)

        addressViewModel =
            ViewModelProvider(
                this,
                factory
            )[AddressViewModel::class.java]
    }

    private fun setUpObserver() {

        addressViewModel.addressState.observe(
            viewLifecycleOwner
        ) { state ->

            when (state) {

                is UIState.Loading -> {
                    // Progress bar later
                }

                is UIState.Success -> {
                    Log.d("ADDRESS_TEST", "userId = $userId")
                    val addresses =
                        state.data.addresses

                    Log.d(
                        "ADDRESS_TEST",
                        "addresses = $addresses"
                    )

                    binding.rvAddresses.adapter =
                        AddressAdapter(
                            addresses
                        ) { address ->

                            selectedAddress = address

                            checkoutViewModel.setSelectedAddress(address)

                        }


                }

                is UIState.Error -> {

                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showAddAddressDialog() {

        val dialog =
            Dialog(requireContext())

        val dialogBinding =
            DialogAddAddressBinding.inflate(
                layoutInflater
            )

        dialog.setContentView(dialogBinding.root)

        dialog.show()

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnSave.setOnClickListener {

            val title =
                dialogBinding.etAddressTitle.text
                    .toString()
                    .trim()

            val address =
                dialogBinding.etAddress.text
                    .toString()
                    .trim()

            if (title.isEmpty() || address.isEmpty()) {

                Toast.makeText(
                    requireContext(),
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val request =
                AddAddressRequest(
                    user_id = userId,
                    title = title,
                    address = address
                )

            addressViewModel.addAddress(request)

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setUpAddAddressObserver() {

        addressViewModel.addAddressState.observe(
            viewLifecycleOwner
        ) { state ->

            when (state) {

                is UIState.Loading -> {
                }

                is UIState.Success -> {

                    Toast.makeText(
                        requireContext(),
                        state.data.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    addressViewModel.getAddresses(userId)
                }

                is UIState.Error -> {

                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}