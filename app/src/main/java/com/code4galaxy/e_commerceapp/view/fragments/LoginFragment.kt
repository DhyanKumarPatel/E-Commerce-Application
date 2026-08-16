package com.code4galaxy.e_commerceapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.databinding.FragmentLoginBinding
import com.code4galaxy.e_commerceapp.model.LoginRequest
import com.code4galaxy.e_commerceapp.network.RetrofitClient
import com.code4galaxy.e_commerceapp.repository.LoginRepositoryImpl
import com.code4galaxy.e_commerceapp.utils.SessionManager
import com.code4galaxy.e_commerceapp.utils.UIState
import com.code4galaxy.e_commerceapp.viewModel.LoginViewModel
import com.code4galaxy.e_commerceapp.viewModel.LoginViewModelFactory

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(
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

        val sessionManager =
            SessionManager(requireContext())

        if (sessionManager.isLoggedIn()) {

            findNavController().navigate(
                R.id.action_loginFragment_to_homeFragment
            )

            return
        }

        setUpViewModel()
        setUpObserver()
        setUpClickListener()
    }

    private fun setUpViewModel() {

        val repository =
            LoginRepositoryImpl(
                RetrofitClient.apiServices
            )

        val factory =
            LoginViewModelFactory(repository)

        viewModel =
            ViewModelProvider(
                this,
                factory
            )[LoginViewModel::class.java]
    }

    private fun setUpClickListener() {

        binding.btnLogin.setOnClickListener {

            val email =
                binding.etEmail.text.toString().trim()

            val password =
                binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    requireContext(),
                    "Please enter email and password",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val request = LoginRequest(
                email_id = email,
                password = password
            )

            viewModel.loginUser(request)
        }

        binding.tvCreateAccount.setOnClickListener {

            findNavController().navigate(
                R.id.registerFragment
            )
        }
    }

    private fun setUpObserver() {

        viewModel.loginState.observe(
            viewLifecycleOwner
        ) { state ->

            when (state) {

                is UIState.Loading -> {

                    binding.btnLogin.isEnabled = false
                }

                is UIState.Success -> {

                    binding.btnLogin.isEnabled = true

                    val response = state.data
                    val user = response.user

                    if (user != null) {

                        val sessionManager =
                            SessionManager(requireContext())

                        sessionManager.saveUser(
                            userId = user.user_id,
                            fullName = user.full_name,
                            email = user.email_id,
                            mobile = user.mobile_no
                        )

//                        Log.d(
//                            "SESSION_TEST",
//                            "userId=${sessionManager.getUserId()}, " +
//                                    "name=${sessionManager.getFullName()}, " +
//                                    "email=${sessionManager.getEmail()}, " +
//                                    "mobile=${sessionManager.getMobile()}, " +
//                                    "loggedIn=${sessionManager.isLoggedIn()}"
//                        )



                        findNavController().navigate(
                            R.id.action_loginFragment_to_homeFragment
                        )
                    }

                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    if (user != null) {

                        Log.d(
                            "LOGIN_TEST",
                            "User ID: ${user.user_id}"
                        )

                    }
                }

                is UIState.Error -> {

                    binding.btnLogin.isEnabled = true

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