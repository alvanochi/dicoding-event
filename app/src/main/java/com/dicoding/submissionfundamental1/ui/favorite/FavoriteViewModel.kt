package com.dicoding.submissionfundamental1.ui.favorite

import androidx.lifecycle.ViewModel
import com.dicoding.submissionfundamental1.EventRepository

class FavoriteViewModel(private val eventRepository: EventRepository) : ViewModel() {
    fun getListFavEvents() = eventRepository.getListFavEvents()
}
