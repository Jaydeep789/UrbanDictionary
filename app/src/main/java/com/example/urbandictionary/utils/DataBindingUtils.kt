package com.example.urbandictionary.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.urbandictionary.domain.Word

@BindingAdapter("wordName")
fun TextView.setWord(word: Word){
    text = word.word
}

@BindingAdapter("wordDefinition")
fun TextView.setDefinition(word: Word){
    text = word.definition
}

@BindingAdapter("wordThumbsUp")
fun TextView.setThumbsUp(word: Word){
    text = word.thumbsUp.toString()
}

@BindingAdapter("wordThumbsDown")
fun TextView.setThumbsDown(word: Word){
    text = word.thumbsDown.toString()
}
