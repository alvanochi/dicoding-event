package com.dicoding.submissionfundamental1.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submissionfundamental1.EventRepository
import com.dicoding.submissionfundamental1.data.local.entity.EventsEntity
import com.dicoding.submissionfundamental1.data.remote.response.Event
import com.dicoding.submissionfundamental1.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel (private val eventRepository: EventRepository) : ViewModel() {

    private val _detailEvent = MutableLiveData<Event>()
    val detailEvent: LiveData<Event> = _detailEvent
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading




     fun showDetailEvent(itemId: Int){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiService().getEventDetail(itemId.toString())
                }

                _detailEvent.value = response.event

                _isLoading.value = false

            } catch (e :Exception){
                _isLoading.value = false
                Log.e("catch", "Error: ${e.message}")
            }
        }
    }

    fun getFavEventById(id: Int) = eventRepository.getFavEventById(id)

    fun saveEvent(events: List<EventsEntity>) {
        viewModelScope.launch {
            eventRepository.setFavEvent(events)
        }
    }

    fun deleteEvent(id: Int) {
        viewModelScope.launch {
            eventRepository.deleteFavEvent(id)
        }
    }
}
