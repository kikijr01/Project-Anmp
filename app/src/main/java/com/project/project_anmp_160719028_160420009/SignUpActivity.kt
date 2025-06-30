package com.project.project_anmp_160719028_160420009

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.project_anmp_160719028_160420009.databinding.ActivitySignUpBinding
import com.project.project_anmp_160719028_160420009.entity.UserEntity
import com.project.project_anmp_160719028_160420009.viewModel.UserViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


        binding.btnCreateAccount.setOnClickListener {
            val username = binding.etCreateUsername.text.toString()
            val password = binding.etCreatePassword.text.toString()
            val repeatPassword = binding.etRepeatPassword.text.toString()
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()

            if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != repeatPassword) {
                Toast.makeText(this, "Password tidak cocok!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.cekUsername(username) { isTaken ->
                if (isTaken) {
                    Toast.makeText(this, "Username sudah digunakan!", Toast.LENGTH_SHORT).show()
                } else {
                    val newUser = UserEntity(
                        username = username,
                        password = password,
                        firstName = firstName,
                        lastName = lastName
                    )
                    viewModel.register(newUser) { success ->
                        if (success) {
                            Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Registrasi gagal, coba lagi.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
