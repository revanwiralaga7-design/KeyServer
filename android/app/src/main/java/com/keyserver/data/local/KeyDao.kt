package com.keyserver.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface KeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(key: KeyEntity): Long

    @Update
    suspend fun update(key: KeyEntity)

    @Delete
    suspend fun delete(key: KeyEntity)

    @Query("SELECT * FROM keys ORDER BY created_at DESC")
    fun getAll(): Flow<List<KeyEntity>>

    @Query("SELECT * FROM keys WHERE id = :id")
    suspend fun getById(id: Long): KeyEntity?

    @Query("SELECT * FROM keys WHERE status = :status ORDER BY created_at DESC")
    fun getByStatus(status: String): Flow<List<KeyEntity>>

    @Query("SELECT * FROM keys WHERE key_value LIKE '%' || :search || '%' OR notes LIKE '%' || :search || '%' ORDER BY created_at DESC")
    fun searchKeys(search: String): Flow<List<KeyEntity>>

    @Query("SELECT COUNT(*) FROM keys WHERE status = :status")
    suspend fun countByStatus(status: String): Int
}
