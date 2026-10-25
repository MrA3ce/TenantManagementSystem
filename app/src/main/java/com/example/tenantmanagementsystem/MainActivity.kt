package com.example.tenantmanagementsystem

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.tenantmanagementsystem.databinding.ActivityMainBinding
import com.example.tenantmanagementsystem.databinding.NavHeaderBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TENANT_LIFECYCLE", "MainActivity: onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Use our Toolbar as the App Bar
        setSupportActionBar(binding.toolbar)

        // 2. Get the NavController from the NavHost
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // 3. Top-level destinations + the drawer (these show ☰ instead of ←)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.dashboardFragment, R.id.addTenantFragment, R.id.viewTenantsFragment),
            binding.drawerLayout
        )

        // 4. Connect the App Bar to navigation
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Update nav drawer header with user email if available
        val email = intent.getStringExtra("EMAIL")
        if (email != null) {
            val headerView = binding.navView.getHeaderView(0)
            val headerBinding = NavHeaderBinding.bind(headerView)
            headerBinding.headerEmailTextView.text = email
        }

        // 5. Handle drawer taps ourselves so Dashboard always opens the Dashboard
        binding.navView.setNavigationItemSelectedListener { item ->
            val handled = if (item.itemId == R.id.dashboardFragment) {
                navController.popBackStack(R.id.dashboardFragment, false)
                true
            } else {
                item.onNavDestinationSelected(navController)
            }
            binding.drawerLayout.closeDrawers()
            handled
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
                true
            }
            else -> item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
