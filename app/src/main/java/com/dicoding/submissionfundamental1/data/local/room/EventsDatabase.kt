package com.dicoding.submissionfundamental1.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.submissionfundamental1.data.local.entity.EventsEntity

@Database(entities = [EventsEntity::class], version = 1, exportSchema = false)
abstract class EventsDatabase: RoomDatabase() {
    abstract fun eventsDao(): EventsDAO


    companion object {
        @Volatile
        private var instance: EventsDatabase? = null
        fun getInstance(context: Context): EventsDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    EventsDatabase::class.java, "Events.db"
                )
//                    .addMigrations(MIGRATION_1_2)
                    .build()
            }
    }
}

//val MIGRATION_1_2 = object : Migration(1, 2) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL("ALTER TABLE events ADD COLUMN link TEXT")
//    }
//}