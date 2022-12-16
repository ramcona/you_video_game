package com.rafli.yourvideogames.ui.activity

import android.os.Bundle
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rafli.yourvideogames.R
import com.rafli.yourvideogames.databinding.ActivityMainBinding
import com.rafli.yourvideogames.helper.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        statusPutih()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_favorite
            )
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.navigation_home) {
                binding.tvTitle.text = getString(R.string.teks_game_for_you)
            } else {
                binding.tvTitle.text = getString(R.string.teks_favorite_games)
            }
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}