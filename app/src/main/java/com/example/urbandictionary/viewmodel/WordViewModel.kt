package com.example.urbandictionary.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.urbandictionary.repository.WordRepository
import kotlinx.coroutines.*

class WordViewModel @ViewModelInject constructor(
    private val repository: WordRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val wordList = repository.searchResult

    val wordNotFound  = repository.wordNotFound

    /**
     *   Flag to display Progress bar. Private in nature so no other
     *   class can set value
     */
    private var _progressBarStatus = MutableLiveData<Boolean>(false)

    /**
     *   Flag to display Progress bar. Exposing livedata to the view
     */
    val progressBarStatus: LiveData<Boolean>
        get() = _progressBarStatus

    init {
        _progressBarStatus.value = true
    }

    /**
     * Network call for searching word & updating database.
     * Using viewModelScope to launch coroutine and performing tasks asynchronously
     */
    fun refreshRepositoryData(str: String) {
        viewModelScope.launch {
            repository.requestData(str)
        }
    }

    fun progressBarOff() {
        _progressBarStatus.value = false
    }

    fun progressBarOn() {
        _progressBarStatus.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
