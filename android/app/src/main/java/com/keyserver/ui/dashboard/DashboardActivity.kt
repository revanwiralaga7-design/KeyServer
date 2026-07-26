package com.keyserver.ui.dashboard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.keyserver.R
import com.keyserver.util.TokenManager

class DashboardActivity : AppCompatActivity() {
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_dashboard)
        tokenManager = TokenManager(this)

        if (!tokenManager.hasToken()) {
            startActivity(android.content.Intent(this, com.keyserver.ui.login.LoginActivity::class.java))
            finish()
            return
        }

        viewModel.loadStats()
        viewModel.stats.observe(this) { stats ->
            findViewById<android.widget.TextView>(R.id.tvStatsTotal).text = "Total Key: ${stats.totalKeys}"
            findViewById<android.widget.TextView>(R.id.tvStatsActive).text = "Aktif: ${stats.activeKeys}"
            findViewById<android.widget.TextView>(R.id.tvStatsExpired).text = "Expired: ${stats.expiredKeys}"
            findViewById<android.widget.TextView>(R.id.tvStatsRevoked).text = "Revoked: ${stats.revokedKeys}"
        }

        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnGenerate).setOnClickListener {
            // Placeholder untuk generate key
        }
    }
}
