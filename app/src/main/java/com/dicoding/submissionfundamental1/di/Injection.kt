package com.dicoding.submissionfundamental1.di

import android.content.Context
import com.dicoding.submissionfundamental1.EventRepository
import com.dicoding.submissionfundamental1.data.local.room.EventsDatabase

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val database = EventsDatabase.getInstance(context)
        val dao = database.eventsDao()
        return EventRepository.getInstance(dao)
    }
}