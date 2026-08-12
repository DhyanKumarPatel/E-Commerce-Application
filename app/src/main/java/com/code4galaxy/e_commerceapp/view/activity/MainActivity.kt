package com.code4galaxy.e_commerceapp.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.databinding.ActivityMainBinding
import com.code4galaxy.e_commerceapp.view.fragments.SearchBarFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var drawerToggle: ActionBarDrawerToggle

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpNavController()

        setUpDrawerToggle()

        setUpNavigationDrawer()

        setUpSearch()
    }


    private fun setUpNavController() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(
                R.id.fragmentContainer
            ) as NavHostFragment

        navController = navHostFragment.navController


        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {

                R.id.smartPhoneFragment -> {
                    binding.toolBar.visibility = View.GONE
                }

                R.id.homeFragment -> {
                    binding.toolBar.visibility = View.VISIBLE
                    binding.toolBar.title = "SUPER CART"
                }

                else -> {
                    binding.toolBar.visibility = View.VISIBLE
                    binding.toolBar.title = destination.label
                }
            }
        }
    }


    private fun setUpNavigationDrawer() {

        binding.navigationView.setNavigationItemSelectedListener { item ->

            when (item.itemId) {

                R.id.menuHome -> {

                    navController.navigate(
                        R.id.homeFragment
                    )
                }


                R.id.menuCart -> {

                    navController.navigate(
                        R.id.cartFragment
                    )
                }


                R.id.menuOrders -> {

                    navController.navigate(
                        R.id.orderFragment
                    )
                }


                R.id.menuProfile -> {

                    navController.navigate(
                        R.id.profileFragment
                    )
                }


                R.id.menuLogout -> {

                    Toast.makeText(
                        this,
                        "Logged Out",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }


            item.isChecked = true

            binding.main.closeDrawer(
                GravityCompat.START
            )

            true
        }
    }


    private fun setUpSearch() {

        binding.toolBar.setOnMenuItemClickListener { item ->

            when (item.itemId) {

                R.id.menuSearch -> {

                    binding.fragmentSearch.visibility =
                        View.VISIBLE

                    binding.fragmentSearch.bringToFront()


                    val currentSearchFragment =
                        supportFragmentManager
                            .findFragmentById(
                                R.id.fragmentSearch
                            )


                    if (currentSearchFragment == null) {

                        supportFragmentManager
                            .beginTransaction()
                            .replace(
                                R.id.fragmentSearch,
                                SearchBarFragment()
                            )
                            .commit()
                    }

                    true
                }


                else -> false
            }
        }
    }


    private fun setUpDrawerToggle() {

        drawerToggle =
            ActionBarDrawerToggle(
                this,
                binding.main,
                binding.toolBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )


        binding.main.addDrawerListener(
            drawerToggle
        )


        drawerToggle.syncState()


        binding.toolBar.setNavigationOnClickListener {

            binding.main.openDrawer(
                GravityCompat.START
            )
        }
    }
}