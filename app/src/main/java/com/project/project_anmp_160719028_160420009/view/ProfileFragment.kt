package com.project.project_anmp_160719028_160420009.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.project.project_anmp_160719028_160420009.databinding.FragmentProfileBinding
import com.project.project_anmp_160719028_160420009.model.UserEntity

import com.project.project_anmp_160719028_160420009.viewModel.UserViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var currentUser: UserEntity? = null
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]


        val sharedPref = requireActivity().getSharedPreferences("login_session", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", null)


        if (username != null) {
            viewModel.fetch(username)
        }


        viewModel.user.observe(viewLifecycleOwner) { user ->
            Log.d("user",user.toString())
            currentUser = user
        }

        binding.btnChangePassword.setOnClickListener {

            val oldPassword = binding.etOldPassword.text.toString()
            val newPassword = binding.etNewPassword.text.toString()
            val repeatPassword = binding.etRepeatPassword.text.toString()

            if (currentUser != null) {
                if (oldPassword != currentUser!!.password) {
                    Toast.makeText(requireContext(), "Old password is incorrect", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (newPassword != repeatPassword) {
                    Toast.makeText(requireContext(), "New and repeat passwords must match", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                currentUser!!.password = newPassword
                viewModel.update(currentUser!!)
                Toast.makeText(requireContext(), "Password updated successfully", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignOut.setOnClickListener {
            sharedPref.edit().clear().apply()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
