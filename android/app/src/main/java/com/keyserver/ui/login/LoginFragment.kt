package com.keyserver.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.keyserver.R
import com.keyserver.util.TokenManager

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var viewModel: LoginViewModel
    private lateinit var tokenManager: TokenManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tokenManager = TokenManager(requireContext())
        viewModel = LoginViewModel()

        if (tokenManager.hasToken()) {
            startActivity(android.content.Intent(requireContext(), com.keyserver.ui.dashboard.DashboardActivity::class.java))
            requireActivity().finish()
            return
        }

        view.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnLogin).setOnClickListener {
            val username = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etUsername).text.toString()
            val password = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etPassword).text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Username dan password wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(username, password) { token ->
                tokenManager.saveToken(token)
                Toast.makeText(requireContext(), "Login berhasil", Toast.LENGTH_SHORT).show()
                startActivity(android.content.Intent(requireContext(), com.keyserver.ui.dashboard.DashboardActivity::class.java))
                requireActivity().finish()
            }
        }
    }
}
