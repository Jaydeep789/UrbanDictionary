package com.example.urbandictionary.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.urbandictionary.domain.Word

/**
 *   Database Entity. Responsible for reading and writing from database
 */
@Entity(tableName = "database_word")
data class DatabaseWord constructor(
    @PrimaryKey
    val word: String,
    val definition: String,
    val thumbsUp: Int,
    val thumbsDown: Int
)

/**
 *   Extension function for converting database objects into domain objects
 */
fun List<DatabaseWord>.asDomainModel(): List<Word> {
    return map { dbWord ->
        Word(
            word = dbWord.word,
            definition = dbWord.definition,
            thumbsUp = dbWord.thumbsUp,
            thumbsDown = dbWord.thumbsDown
        )
    }
}
