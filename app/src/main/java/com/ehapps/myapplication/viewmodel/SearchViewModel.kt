package com.ehapps.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ehapps.myapplication.manager.YouTubeManager
import com.ehapps.myapplication.model.YouTubeVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {

    val videosStateFlow = MutableStateFlow<List<YouTubeVideo>>(emptyList())

    fun search(query: String) {
        if (query.isNotBlank()) {
            viewModelScope.launch(Dispatchers.Default) {
                val videosList = YouTubeManager.getVideosFromQuery(query)
                Log.d(TAG, "Videos: ${videosList.size}")
                videosStateFlow.value = videosList
            }
        }
    }


    companion object {
        const val TAG = "SearchViewModel"
    }
}