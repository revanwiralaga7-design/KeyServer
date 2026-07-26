package com.keyserver.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "keys")
data class KeyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val keyValue: String,
    val status: String,
    val durationDays: Int,
    val deviceId: String?,
    val userName: String?,
    val notes: String?,
    val createdAt: Long = System.currentTimeMillis(),
    val expiredAt: Long = System.currentTimeMillis() + (durationDays * 24L * 60 * 60 * 1000)
)
