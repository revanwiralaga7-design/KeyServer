package com.keyserver.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [KeyEntity::class], version = 1)
abstract class KeyDatabase : RoomDatabase() {
    abstract fun keyDao(): KeyDao
}

interface KeyDao {
    // Placeholder untuk operasi Room
}
