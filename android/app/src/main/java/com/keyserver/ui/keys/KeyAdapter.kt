package com.keyserver.ui.keys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.keyserver.R
import com.keyserver.data.local.KeyEntity

class KeyAdapter(
    private var keys: List<KeyEntity> = emptyList(),
    private val onItemClick: (KeyEntity) -> Unit
) : RecyclerView.Adapter<KeyAdapter.KeyViewHolder>() {

    inner class KeyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvKey: TextView = itemView.findViewById(R.id.tvKeyValue)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        private val tvNotes: TextView = itemView.findViewById(R.id.tvNotes)

        fun bind(key: KeyEntity) {
            tvKey.text = key.keyValue
            tvStatus.text = key.status.uppercase()
            tvNotes.text = key.notes ?: ""
            itemView.setOnClickListener { onItemClick(key) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_key, parent, false)
        return KeyViewHolder(view)
    }

    override fun onBindViewHolder(holder: KeyViewHolder, position: Int) {
        holder.bind(keys[position])
    }

    override fun getItemCount(): Int = keys.size

    fun submitList(newList: List<KeyEntity>) {
        keys = newList
        notifyDataSetChanged()
    }
}
