package com.project.project_anmp_160719028_160420009.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.project_anmp_160719028_160420009.databinding.FragmentProfileBinding
import com.project.project_anmp_160719028_160420009.viewModel.UserViewModel

class ProfileFragment : Fragment(), UserViewModel.ProfileActionListener {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.listener = this

        val sharedPref = requireActivity().getSharedPreferences("login_session", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", null)

        if (username != null) {
            viewModel.fetch(username)
        }
    }

    override fun onSignOut() {
        val sharedPref = requireActivity().getSharedPreferences("login_session", Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finish()
    }

    override fun onShowMessage(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

