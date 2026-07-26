package com.keyserver.ui.keys

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.keyserver.data.local.KeyEntity
import com.keyserver.data.repository.KeyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class KeyListViewModel(private val repository: KeyRepository) : ViewModel() {

    private val _keys = repository.getAllKeys().asLiveData()
    val keys = _keys

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _filterStatus = MutableStateFlow("all")
    val filterStatus = _filterStatus.asStateFlow()

    fun setSearch(query: String) {
        _searchQuery.value = query
    }

    fun setFilter(status: String) {
        _filterStatus.value = status
    }

    suspend fun deleteKey(key: KeyEntity) {
        repository.deleteKey(key)
    }

    suspend fun revokeKey(key: KeyEntity) {
        repository.updateKey(key.copy(status = "revoked"))
    }

    suspend fun activateKey(key: KeyEntity) {
        repository.updateKey(key.copy(status = "active"))
    }
}
