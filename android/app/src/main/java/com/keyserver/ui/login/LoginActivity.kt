package com.keyserver.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.keyserver.R
import com.keyserver.util.TokenManager

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)
        tokenManager = TokenManager(this)

        if (tokenManager.hasToken()) {
            startActivity(android.content.Intent(this, com.keyserver.ui.dashboard.DashboardActivity::class.java))
            finish()
            return
        }

        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnLogin).setOnClickListener {
            val username = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etUsername).text.toString()
            val password = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etPassword).text.toString()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan password wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.login(username, password) { token ->
                tokenManager.saveToken(token)
                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                startActivity(android.content.Intent(this, com.keyserver.ui.dashboard.DashboardActivity::class.java))
                finish()
            }
        }
    }
}
