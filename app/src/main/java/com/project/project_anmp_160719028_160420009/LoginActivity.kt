package com.project.project_anmp_160719028_160420009

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.project.project_anmp_160719028_160420009.databinding.ActivityLoginBinding
import com.project.project_anmp_160719028_160420009.viewModel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        val pref = getSharedPreferences("login_session", Context.MODE_PRIVATE)

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(username, password) { user ->
                    Log.d("USER", user.toString())

                    if (user != null) {
                        // Simpan ke SharedPreferences
                        pref.edit().putString("username", user.username).apply()

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Kolom harus diisi semua", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }
}