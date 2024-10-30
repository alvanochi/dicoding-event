package com.dicoding.submissionfundamental1.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.submissionfundamental1.data.local.entity.EventsEntity

@Dao
interface EventsDAO {
    @Query("SELECT * FROM events ORDER BY idEvent ASC")
    fun getListFavEvents(): LiveData<List<EventsEntity>>

    @Query("SELECT * FROM events WHERE idEvent = :id")
    fun getFavEventById(id: Int): LiveData<EventsEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvent(event: List<EventsEntity>)

    @Query("DELETE FROM events WHERE idEvent = :id")
    suspend fun deleteById(id: Int)

}