package com.keyserver.ui.keys

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.keyserver.R
import com.keyserver.data.local.KeyEntity
import com.keyserver.data.repository.KeyRepository
import com.keyserver.util.TokenManager

class KeyListActivity : androidx.appcompat.app.AppCompatActivity() {
    private lateinit var adapter: KeyAdapter
    private lateinit var tokenManager: TokenManager
    private val viewModel by lazy {
        val dao = (application as? com.keyserver.KeyServerApp)?.database?.keyDao()
            ?: throw IllegalStateException("Database belum ready")
        val repo = KeyRepository(dao)
        KeyListViewModel(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_key_list)
        tokenManager = TokenManager(this)

        adapter = KeyAdapter(
            emptyList(),
            onItemClick = { key -> Toast.makeText(this, "Key: ${key.keyValue}", Toast.LENGTH_SHORT).show() },
            onDelete = { key -> viewModel.deleteKey(key) },
            onRevoke = { key -> viewModel.revokeKey(key) },
            onActivate = { key -> viewModel.activateKey(key) }
        )

        val recycler = findViewById<RecyclerView>(R.id.recyclerKeys)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }
}
