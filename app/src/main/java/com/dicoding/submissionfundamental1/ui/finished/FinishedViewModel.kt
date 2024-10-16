package com.dicoding.submissionfundamental1.ui.finished

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissionfundamental1.data.response.EventResponse
import com.dicoding.submissionfundamental1.data.response.ListEventsItem
import com.dicoding.submissionfundamental1.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel : ViewModel() {

    private val _finishedEvent = MutableLiveData<List<ListEventsItem>>()
    val finishedEvent: LiveData<List<ListEventsItem>> = _finishedEvent
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    init{
        showEvent()
    }


    private fun showEvent(query: String? = null){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFinishedEvent(query = query)
        client.enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _finishedEvent.value = response.body()?.listEvents
                    if (response.body()?.listEvents?.isEmpty() == true){
                        _isEmpty.value = true
                    }else {
                        _isEmpty.value = false
                    }
                }else {
                    Log.e("Finished", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Finished", "onFailure: ${t.message.toString()}")
            }
        })
    }


     fun showFilter(query: String?){
        showEvent(query)
    }
}