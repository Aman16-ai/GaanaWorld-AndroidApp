package com.example.gaanaworld.ui

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.gaanaworld.R
import com.example.gaanaworld.ui.viewmodels.SongsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var toolbar : Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_home_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)

        bottomNavigationView = findViewById(R.id.bottom_nav)


        navController.addOnDestinationChangedListener {_,destination,_->
            if(destination.id == R.id.musicScreenFragment) {
                bottomNavigationView.visibility = View.GONE
            }
            else if(destination.id == R.id.add_music_item) {
                bottomNavigationView.visibility = View.GONE
            }
            else {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }
}