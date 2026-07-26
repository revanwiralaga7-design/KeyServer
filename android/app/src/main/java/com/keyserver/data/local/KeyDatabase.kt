package com.keyserver.data.local

import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [KeyEntity::class], version = 1, exportSchema = false)
abstract class KeyDatabase : RoomDatabase() {
    abstract fun keyDao(): KeyDao
}

interface KeyDao {
    // Placeholder untuk operasi Room
}
