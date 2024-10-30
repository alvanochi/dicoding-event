package com.dicoding.submissionfundamental1

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissionfundamental1.di.Injection
import com.dicoding.submissionfundamental1.ui.detail.DetailViewModel
import com.dicoding.submissionfundamental1.ui.favorite.FavoriteViewModel
import com.dicoding.submissionfundamental1.ui.setting.SettingPreferences
import com.dicoding.submissionfundamental1.ui.setting.SettingViewModel

class ViewModelFactory(private val eventRepository: EventRepository?, private val pref: SettingPreferences?):
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                eventRepository?.let { FavoriteViewModel(it) } as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                eventRepository?.let { DetailViewModel(it) } as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                eventRepository?.let { DetailViewModel(it) } as T
            }
            modelClass.isAssignableFrom(SettingViewModel::class.java) ->{
                pref?.let { SettingViewModel(it) } as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context), null)
            }.also { instance = it }
    }
}