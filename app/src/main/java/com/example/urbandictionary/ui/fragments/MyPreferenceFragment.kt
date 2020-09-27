package com.example.urbandictionary.ui.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.urbandictionary.R

open class MyPreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
    }
}
