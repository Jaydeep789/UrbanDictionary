package com.example.urbandictionary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.urbandictionary.database.DatabaseWord
import com.example.urbandictionary.database.DatabaseWordDao
import com.example.urbandictionary.database.WordDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class WordDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var wordDatabase: WordDatabase

    private fun getWordDao(): DatabaseWordDao {
        return wordDatabase.wordDao()
    }

    @Before
    fun init() {
        wordDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WordDatabase::class.java
        ).build()
    }

    @After
    fun finish() {
        wordDatabase.close()
    }

    /**
     * Insert & check if database is not empty
     */
    @Test
    fun insertCheck() = runBlocking{

        val searchWord = "list"

        val wordList: List<DatabaseWord> = listOf(
            DatabaseWord(
                word = "list",
                definition = "1. the last tool for lazy writers who want to show how hip their knowledge is.",
                thumbsUp = 26,
                thumbsDown = 20
            ),
            DatabaseWord(
                word = "list",
                definition = "a list of people that you are [going] to [kill] [soon]",
                thumbsUp = 2,
                thumbsDown = 1
            ),
            DatabaseWord(
                word = "list",
                definition = "What you say to “[low-key]” tell someone that someone else (or you) is on [the sex] [offender] list.",
                thumbsUp = 9,
                thumbsDown = 0
            ),
            DatabaseWord(
                word = "list",
                definition = "[you are] [on that list] when you have a [STD]",
                thumbsUp = 7,
                thumbsDown = 0
            )
        )

        // Insert list in Database
        getWordDao().insertAll(wordList)

        // read the list
        val retrievedWords = getWordDao().searchWordFromDb(searchWord)

        //Assert
        assertNotNull(retrievedWords)
        assertEquals(wordList[0].word, retrievedWords[0].word)
    }
}
