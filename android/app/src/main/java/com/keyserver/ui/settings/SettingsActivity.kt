package com.keyserver.ui.settings

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.keyserver.R
import com.keyserver.util.TokenManager

class SettingsActivity : AppCompatActivity() {
    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_settings)
        tokenManager = TokenManager(this)

        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnLogout)?.setOnClickListener {
            tokenManager.clearToken()
            startActivity(android.content.Intent(this, com.keyserver.ui.login.LoginActivity::class.java))
            finish()
        }
    }
}
