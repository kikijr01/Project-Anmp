package com.project.project_anmp_160719028_160420009

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.project_anmp_160719028_160420009.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cek SharedPreferences, kalau udah login langsung ke MainActivity
        val pref = getSharedPreferences("login_session", Context.MODE_PRIVATE)
        val savedUsername = pref.getString("username", null)
        Log.d("username", savedUsername.toString());
        if (savedUsername == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)
    }
}