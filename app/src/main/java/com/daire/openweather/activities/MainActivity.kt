package com.daire.openweather.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import com.daire.openweather.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set up navigation
        val control = findNavController(R.id.mainNavFragment)
        NavigationUI.setupWithNavController(navigation, control)
        navigation.itemIconTintList = null
        navigation.setOnNavigationItemSelectedListener {item ->
            onNavDestinationSelected(item, control)
        }
    }


}
