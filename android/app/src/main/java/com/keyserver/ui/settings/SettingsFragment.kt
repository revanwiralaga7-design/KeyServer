package com.keyserver.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import com.keyserver.R
import com.keyserver.util.TokenManager

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var tokenManager: TokenManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tokenManager = TokenManager(requireContext())

        view.findViewById<com.google.android.material.switchmaterial.SwitchMaterial>(R.id.switchDarkMode)?.setOnCheckedChangeListener { _, isChecked ->
            // Placeholder dark mode toggle
        }

        view.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnLogout)?.setOnClickListener {
            tokenManager.clearToken()
            startActivity(android.content.Intent(requireContext(), com.keyserver.ui.login.LoginActivity::class.java))
            requireActivity().finish()
        }
    }
}
