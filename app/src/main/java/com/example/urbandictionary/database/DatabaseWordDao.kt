package com.example.urbandictionary.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DatabaseWordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<DatabaseWord>): List<Long>

    @Query("SELECT * FROM database_word WHERE word LIKE :word")
    suspend fun searchWordFromDb(word: String?): List<DatabaseWord>
}
