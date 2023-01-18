package com.example.csiapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import me.ibrahimsn.lib.OnItemSelectedListener
import me.ibrahimsn.lib.SmoothBottomBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bottomNavigationView = findViewById<View>(R.id.bottom_navigation) as SmoothBottomBar
        bottomNavigationView.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelect(i: Int): Boolean {
                when (i) {

                    // For bottom navigaton

//                    0 -> supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragment_container, Study_Fragment()).commit()
//                    1 -> supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragment_container, Analysis_Fragment()).commit()
//                    3 -> supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragment_container, Profile_Fragment()).commit()
//                    2 -> supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragment_container, Test_Fragment()).commit()
//                    else -> supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragment_container, Study_Fragment()).commit()
                }
                return true
            }
        }


    }
}