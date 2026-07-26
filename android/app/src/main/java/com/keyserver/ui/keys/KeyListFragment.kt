package com.keyserver.ui.keys

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.keyserver.R
import com.keyserver.data.local.KeyEntity

class KeyListFragment : Fragment(R.layout.fragment_key_list) {

    private lateinit var adapter: KeyAdapter
    private lateinit var viewModel: KeyListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = KeyAdapter(
            emptyList(),
            onItemClick = { key -> Toast.makeText(requireContext(), "Key: ${key.keyValue}", Toast.LENGTH_SHORT).show() },
            onDelete = { key -> viewModel.deleteKey(key) },
            onRevoke = { key -> viewModel.revokeKey(key) },
            onActivate = { key -> viewModel.activateKey(key) }
        )

        val recycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerKeys)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
    }
}
