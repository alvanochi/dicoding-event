package com.dicoding.submissionfundamental1.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionfundamental1.data.remote.response.ListEventsItem
import com.dicoding.submissionfundamental1.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel: ViewModel() {


    private val _upcomingEvent = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvent: LiveData<List<ListEventsItem>> = _upcomingEvent

    private val _finishedEvent = MutableLiveData<List<ListEventsItem>>()
    val finishedEvent: LiveData<List<ListEventsItem>> = _finishedEvent

    init{
        showFinishedEvent()
        showUpcomingEvent()
    }


    private fun showFinishedEvent(){
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiService().getFinishedEvent(limit = 5)
                }

                _finishedEvent.value = response.listEvents

            } catch (e: Exception){
                Log.e("catch", "HTTP error: ${e.message}")
            }
        }
    }

    private fun showUpcomingEvent(){
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiService().getUpcomingEvent()
                }

                _upcomingEvent.value = response.listEvents

            } catch (e: Exception){
                Log.e("catch", "HTTP error: ${e.message}")
            }
        }
    }
}