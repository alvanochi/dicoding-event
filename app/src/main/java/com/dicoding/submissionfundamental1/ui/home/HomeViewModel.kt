package com.dicoding.submissionfundamental1.ui.home

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

class HomeViewModel : ViewModel() {

    private val _upcomingEvent = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvent: LiveData<List<ListEventsItem>> = _upcomingEvent

    private val _finishedEvent = MutableLiveData<List<ListEventsItem>>()
    val finishedEvent: LiveData<List<ListEventsItem>> = _finishedEvent

    init{
        showFinishedEvent()
        showUpcomingEvent()
    }


    private fun showFinishedEvent(){
        val client = ApiConfig.getApiService().getFinishedEvent(limit = 5)
        client.enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful){
                    _finishedEvent.value = response.body()?.listEvents

                }else {
                    Log.e("HomeFrg", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Log.e("HomeFrg", "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun showUpcomingEvent(){
        val client = ApiConfig.getApiService().getUpcomingEvent(limit = 5)
        client.enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful){
                    _upcomingEvent.value = response.body()?.listEvents

                }else {
                    Log.e("HomeFrg", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Log.e("HomeFrg", "onFailure: ${t.message.toString()}")

            }
        })
    }
}