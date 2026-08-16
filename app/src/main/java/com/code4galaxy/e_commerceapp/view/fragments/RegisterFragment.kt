package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.databinding.FragmentRegisterBinding
import com.code4galaxy.e_commerceapp.model.RegisterRequest
import com.code4galaxy.e_commerceapp.network.RetrofitClient
import com.code4galaxy.e_commerceapp.repository.RegisterRepositoryImpl
import com.code4galaxy.e_commerceapp.utils.UIState
import com.code4galaxy.e_commerceapp.viewModel.RegisterViewModel
import com.code4galaxy.e_commerceapp.viewModel.RegisterViewModelFactory

class RegisterFragment: Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()
        setUpObserver()
        setUpClickListeners()
    }

    private fun setUpObserver(){
        viewModel.registerState.observe(
            viewLifecycleOwner
        ) { state ->

            when (state) {

                is UIState.Loading -> {

                    binding.btnRegister.isEnabled = false
                }

                is UIState.Success -> {

                    binding.btnRegister.isEnabled = true

                    Toast.makeText(
                        requireContext(),
                        state.data.message,
                        Toast.LENGTH_SHORT
                    ).show()

                }

                is UIState.Error -> {

                    binding.btnRegister.isEnabled = true

                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setUpClickListeners() {
        binding.btnRegister.setOnClickListener {

            val fullName =
                binding.etFullName.text.toString().trim()

            val mobile =
                binding.etMobile.text.toString().trim()

            val email =
                binding.etEmail.text.toString().trim()

            val password =
                binding.etPassword.text.toString().trim()

            if (
                fullName.isEmpty() ||
                mobile.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty()
            ) {

                Toast.makeText(
                    requireContext(),
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val request =
                RegisterRequest(
                    full_name = fullName,
                    mobile_no = mobile,
                    email_id = email,
                    password = password
                )

            viewModel.registerUser(request)
        }


        binding.tvAlreadyAccount.setOnClickListener {

            findNavController().navigate(
                R.id.loginFragment
            )


        }
    }

    private fun setUpViewModel() {
        val repository = RegisterRepositoryImpl(RetrofitClient.apiServices)
        val factory = RegisterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]
    }
}