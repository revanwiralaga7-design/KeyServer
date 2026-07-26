package com.keyserver.data.repository

import com.keyserver.data.local.KeyDao
import com.keyserver.data.local.KeyEntity
import kotlinx.coroutines.flow.Flow

class KeyRepository(private val dao: KeyDao) {

    fun getAllKeys(): Flow<List<KeyEntity>> = dao.getAll()

    fun getKeysByStatus(status: String): Flow<List<KeyEntity>> = dao.getByStatus(status)

    fun searchKeys(query: String): Flow<List<KeyEntity>> = dao.searchKeys(query)

    suspend fun insertKey(key: KeyEntity): Long = dao.insert(key)

    suspend fun updateKey(key: KeyEntity) = dao.update(key)

    suspend fun deleteKey(key: KeyEntity) = dao.delete(key)

    suspend fun getKeyById(id: Long): KeyEntity? = dao.getById(id)

    suspend fun countByStatus(status: String): Int = dao.countByStatus(status)
}
