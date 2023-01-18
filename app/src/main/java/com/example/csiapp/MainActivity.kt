package com.example.csiapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.csiapp.Fragments.BlogsFragment
import com.example.csiapp.Fragments.ChatBotFragment
import com.example.csiapp.Fragments.DashboardFragment
import com.example.csiapp.Fragments.DiaryFragment
import me.ibrahimsn.lib.OnItemSelectedListener
import me.ibrahimsn.lib.SmoothBottomBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, DashboardFragment())
            .commit()

        val sf = getSharedPreferences("ShredPref", MODE_PRIVATE)
        val bottomNavigationView = findViewById<View>(R.id.bottom_navigation) as SmoothBottomBar
        bottomNavigationView.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelect(i: Int): Boolean {
                when (i) {
                    0 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, DashboardFragment()).commit()
                    1 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, DiaryFragment()).commit()
                    3 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, BlogsFragment()).commit()
                    2 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ChatBotFragment()).commit()
                    else -> supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, DashboardFragment()).commit()
                }
                return true
            }
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity();
    }
}