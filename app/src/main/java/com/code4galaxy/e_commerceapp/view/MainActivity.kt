package com.code4galaxy.e_commerceapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.code4galaxy.e_commerceapp.R
import com.code4galaxy.e_commerceapp.databinding.ActivityMainBinding
import com.code4galaxy.e_commerceapp.view.fragments.CartFragment
import com.code4galaxy.e_commerceapp.view.fragments.HomeFragment
import com.code4galaxy.e_commerceapp.view.fragments.OrderFragment
import com.code4galaxy.e_commerceapp.view.fragments.ProfileFragment
import com.code4galaxy.e_commerceapp.view.fragments.SearchBarFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpDrawerToggle()
        setUpNavigationDrawer()
        setUpSearch()

        if (savedInstanceState == null) {
            openFragment(
                fragment = HomeFragment(),
                title = "SUPER CART"
            )
        }


    }

    private fun setUpSearch() {
        binding.toolBar.setOnMenuItemClickListener { item ->

            when (item.itemId) {

                R.id.menuSearch -> {

                    binding.fragmentSearch.visibility = View.VISIBLE
                    binding.fragmentSearch.bringToFront()

                    val currentSearchFragment =
                        supportFragmentManager.findFragmentById(
                            R.id.fragmentSearch
                        )

                    if (currentSearchFragment == null) {
                        supportFragmentManager.beginTransaction()
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




    private fun setUpNavigationDrawer() {
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.menuHome -> {
                    openFragment(fragment = HomeFragment(), title = "SUPER CART")
                }
                R.id.menuCart -> {
                    openFragment(fragment = CartFragment(), title = "Cart")
                }
                R.id.menuOrders -> {
                    openFragment(fragment = OrderFragment(), title = "Orders")
                }
                R.id.menuProfile -> {
                    openFragment(fragment = ProfileFragment(), title = "Profile")
                }
                R.id.menuLogout -> {
                    Toast.makeText(this,"Logged Out", Toast.LENGTH_LONG).show()
                }
            }
            item.isChecked = true
            binding.main.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun openFragment(fragment: Fragment, title: String) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (currentFragment?.javaClass == fragment?.javaClass){
            binding.toolBar.title = title
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        binding.toolBar.title = title
    }

    private fun setUpDrawerToggle() {


        drawerToggle = ActionBarDrawerToggle(this,binding.main,binding.toolBar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)

        binding.main.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        binding.toolBar.setNavigationOnClickListener {
            binding.main.openDrawer(GravityCompat.START)
        }
    }
}