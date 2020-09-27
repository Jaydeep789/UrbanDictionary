package com.example.urbandictionary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.urbandictionary.R
import com.example.urbandictionary.repository.WordRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
