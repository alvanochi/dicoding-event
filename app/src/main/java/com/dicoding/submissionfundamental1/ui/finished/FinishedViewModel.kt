package com.dicoding.submissionfundamental1.ui.finished

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

class FinishedViewModel : ViewModel() {

    private val _finishedEvent = MutableLiveData<List<ListEventsItem>>()
    val finishedEvent: LiveData<List<ListEventsItem>> = _finishedEvent
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty


    init {
        showEvent()
    }


    private fun showEvent(query: String? = null){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiService().getFinishedEvent(query = query)
                }

                _isLoading.value = false

                _finishedEvent.value = response.listEvents

                _isEmpty.value = response.listEvents.isEmpty()


            } catch (e: Exception){
                _isLoading.value = false
                Log.e("catch", "Error: ${e.message}")
            }
        }
    }


    fun showFilter(query: String?){
        showEvent(query)
    }
}