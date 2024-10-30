package com.dicoding.submissionfundamental1

import androidx.lifecycle.LiveData
import com.dicoding.submissionfundamental1.data.local.entity.EventsEntity
import com.dicoding.submissionfundamental1.data.local.room.EventsDAO

class EventRepository(
    private val eventsDAO: EventsDAO,
){


    fun getListFavEvents(): LiveData<List<EventsEntity>> {
        return eventsDAO.getListFavEvents()
    }

    fun getFavEventById(id: Int): LiveData<EventsEntity> {
        return eventsDAO.getFavEventById(id)
    }


    suspend fun setFavEvent(events: List<EventsEntity>){
        eventsDAO.insertEvent(events)
    }

    suspend fun deleteFavEvent(id: Int){
        eventsDAO.deleteById(id)
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            eventsDao: EventsDAO,
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(eventsDao)
            }.also { instance = it }
    }
}