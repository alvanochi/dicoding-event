package com.dicoding.submissionfundamental1.ui.upcoming

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissionfundamental1.data.response.EventResponse
import com.dicoding.submissionfundamental1.data.response.ListEventsItem
import com.dicoding.submissionfundamental1.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {

    private val _upcomingEvent = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvent: LiveData<List<ListEventsItem>> = _upcomingEvent
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    @SuppressLint("StaticFieldLeak")
    private var context: Context? = null

    init{
        showEvent()
    }


    private fun showEvent(query: String? = null){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUpcomingEvent(query = query)
        client.enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _upcomingEvent.value = response.body()?.listEvents
                    if (response.body()?.listEvents?.isEmpty() == true){
                        _isEmpty.value = true
                    }else {
                        _isEmpty.value = false
                    }
                }else {
                    Log.e("Upcoming", "onFailure: ${response.message()}")
                    _isLoading.value = true

                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Log.e("Upcoming", "onFailure: ${t.message.toString()}")
                if(t.message == "timeout"){
                    Toast.makeText(context, "Periksa koneksi internet anda!", Toast.LENGTH_LONG).show()
                }
            }
        })
    }



    fun showFilter(query: String?){
        showEvent(query)
    }
}