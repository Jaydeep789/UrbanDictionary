package com.example.urbandictionary.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import com.example.urbandictionary.BuildConfig
import com.example.urbandictionary.database.WordDatabase
import com.example.urbandictionary.database.asDomainModel
import com.example.urbandictionary.domain.Word
import com.example.urbandictionary.network.WordServiceApi
import com.example.urbandictionary.network.asDatabaseModel
import com.example.urbandictionary.utils.Constants
import timber.log.Timber

@Suppress("DEPRECATION")
class WordRepository constructor(
    private val apiService: WordServiceApi,
    private val context: Context,
    private val database: WordDatabase
) {
    private var _searchResult = MutableLiveData<List<Word>>()
    val searchResult: LiveData<List<Word>> get() = _searchResult

    private val _wordNotFound = MutableLiveData<String>()
    val wordNotFound: LiveData<String> get() = _wordNotFound

    private fun checkNetworkConnection(): Boolean {
        val result: Boolean
        val connectivity =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkType = connectivity.activeNetwork ?: return false
            val activeNetwork = connectivity.getNetworkCapabilities(networkType) ?: return false

            when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val networkInfo = connectivity.activeNetworkInfo ?: return false
            networkInfo.isConnected
        }
        Timber.i(result.toString())
        return result
    }

    suspend fun requestData(str: String) {
        if (checkNetworkConnection()) {
            withNetworkConnection(str)
        } else {
            withoutNetworkConnection(str)
        }
    }

    private suspend fun withoutNetworkConnection(str: String) {
        val dbList = database.wordDao().searchWordFromDb("%$str%").asDomainModel()
        _searchResult.postValue(dbList)
        if (dbList.isEmpty()) {
            _wordNotFound.value = Constants.EMPTY_WITHOUT_NETWORK
        }
    }

    private suspend fun withNetworkConnection(str: String) {
        val newWord = apiService.getWord(str, BuildConfig.API_KEY)
        val list = database.wordDao().insertAll(newWord.asDatabaseModel())
        Timber.i(list.toString())
        Timber.i(newWord.toString())
        if (list.isEmpty()) {
            _wordNotFound.value = Constants.EMPTY_WITH_NETWORK
            val networkList = database.wordDao().searchWordFromDb("%$str%").asDomainModel()
            _searchResult.postValue(networkList)
        } else {
            _wordNotFound.value = Constants.LIST_WITH_NETWORK
            val networkList = database.wordDao().searchWordFromDb("%$str%").asDomainModel()
            _searchResult.postValue(networkList)
        }
    }
}
