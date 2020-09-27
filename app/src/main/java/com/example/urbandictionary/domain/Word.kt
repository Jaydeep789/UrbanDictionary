package com.example.urbandictionary.domain

/**
 *   Domain objects for displaying data in app
 */
data class Word(
    val word: String,
    val definition: String,
    val thumbsUp: Int,
    val thumbsDown: Int
)
