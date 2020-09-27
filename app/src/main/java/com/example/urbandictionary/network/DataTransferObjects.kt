package com.example.urbandictionary.network

import com.example.urbandictionary.database.DatabaseWord
import com.google.gson.annotations.SerializedName

/**
 *  DataTransferObjects go in this file. These are basically responsible for parsing network
 *  responses from server.
 *
 */

/**
 *   Actual data from network which can be displayed
 */
data class NetworkWord(
    val word: String,
    val definition: String,
    @SerializedName("thumbs_up")
    val thumbsUp: Int,
    @SerializedName("thumbs_down")
    val thumbsDown: Int
)

data class NetworkWordContainer(
    @SerializedName("list")
    val word: List<NetworkWord>
)

/**
 * Extension function to convert network result into database objects
 */
fun NetworkWordContainer.asDatabaseModel(): List<DatabaseWord> {
    return word.map { networkWord ->
        DatabaseWord(
            word = networkWord.word,
            definition = networkWord.definition,
            thumbsUp = networkWord.thumbsUp,
            thumbsDown = networkWord.thumbsDown
        )
    }
}
