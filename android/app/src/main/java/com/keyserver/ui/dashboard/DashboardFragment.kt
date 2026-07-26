package com.keyserver.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.keyserver.R

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private lateinit var viewModel: DashboardViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        viewModel.loadStats()
        viewModel.stats.observe(viewLifecycleOwner) { stats ->
            view.findViewById<android.widget.TextView>(R.id.tvStatsTotal).text = "Total Key: ${stats.totalKeys}"
            view.findViewById<android.widget.TextView>(R.id.tvStatsActive).text = "Aktif: ${stats.activeKeys}"
            view.findViewById<android.widget.TextView>(R.id.tvStatsExpired).text = "Expired: ${stats.expiredKeys}"
            view.findViewById<android.widget.TextView>(R.id.tvStatsRevoked).text = "Revoked: ${stats.revokedKeys}"
        }
    }
}
