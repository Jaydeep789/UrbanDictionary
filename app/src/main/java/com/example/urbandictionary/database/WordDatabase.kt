package com.example.urbandictionary.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DatabaseWord::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): DatabaseWordDao
}
