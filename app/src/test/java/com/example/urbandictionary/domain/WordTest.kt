package com.example.urbandictionary.domain

import org.junit.Assert.*
import org.junit.Test

class WordTest {
    @Test
    fun testWordsAreEqual() {
        val testWord1 = Word("Welcome", "to be awesome beyond", 350, thumbsDown = 76)
        val testWord2 = Word("Welcome", "to be awesome beyond", 350, thumbsDown = 76)

        assertEquals(testWord1, testWord2)
    }

    @Test
    fun testWordsAreNotEqual() {
        val testWord1 = Word("Welcome", "to be awesome beyond", 350, thumbsDown = 76)
        val testWord2 = Word("list", "to be awesome beyond", 350, thumbsDown = 76)

        assertNotEquals(testWord1, testWord2)
    }
}
