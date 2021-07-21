package com.github.andresvasquez.kotlinevents.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.github.andresvasquez.event_repository.utils.Constants
import com.github.andresvasquez.kotlinevents.R
import com.github.andresvasquez.kotlinevents.databinding.ActivityMainBinding
import com.github.andresvasquez.kotlinevents.databinding.NavHeaderBinding
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val viewModel: MainViewModel by viewModel()

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private var navViewHeaderBinding: NavHeaderBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        navController = this.findNavController(R.id.nav_host_fragment)
        // prevent nav gesture if not on start destination
        navController.addOnDestinationChangedListener { _: NavController, nd: NavDestination, _: Bundle? ->
            when (nd.id) {
                R.id.splashFragment -> {
                    supportActionBar?.hide()
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.eventListFragment -> {
                    supportActionBar?.show()
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

                }
                else -> {
                    supportActionBar?.show()
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
            }
            updateSearchInfo(binding)
        }

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
        binding.navView.setNavigationItemSelectedListener(this)
        binding.lifecycleOwner = this
        saveDefaultSearchInfo()
    }

    private fun saveDefaultSearchInfo() {
        viewModel.saveSearch(Constants.DEFAULT_NEXT_TRIP)
    }

    private fun updateSearchInfo(binding: ActivityMainBinding) {
        viewModel.getSearch()?.let {
            val viewHeader = binding.navView.getHeaderView(0)
            if (navViewHeaderBinding == null) {
                navViewHeaderBinding = NavHeaderBinding.bind(viewHeader)
            }
            navViewHeaderBinding!!.nextTrip = it
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    override fun onNavigationItemSelected(@NonNull menuItem: MenuItem): Boolean {
        drawerLayout.closeDrawers()
        when (menuItem.itemId) {
            R.id.about -> {
                //TODO display a nice UI
            }
        }
        return true
    }
}