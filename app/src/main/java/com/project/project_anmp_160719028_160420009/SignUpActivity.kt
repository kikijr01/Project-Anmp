package com.project.project_anmp_160719028_160420009

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.project_anmp_160719028_160420009.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Contoh penggunaan:
        binding.btnBack.setOnClickListener {
            finish() // kembali ke LoginActivity
        }

        binding.btnCreateAccount.setOnClickListener {
            // Validasi dan proses pembuatan akun
        }
    }
}