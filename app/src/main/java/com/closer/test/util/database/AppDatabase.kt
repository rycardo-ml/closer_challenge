package com.closer.test.util.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.closer.test.util.database.dao.PostalCodeDAO
import com.closer.test.util.model.PostalCode
import com.closer.test.util.model.PostalCodeFTS

@Database(
    entities = [PostalCode::class, PostalCodeFTS::class]
    , version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun postalCodeDAO(): PostalCodeDAO

    companion object {

        @Volatile // All threads have immediate access to this property
        private var instance: AppDatabase? = null

        private val LOCK = Any() // Makes sure no threads making the same thing at the same time

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "closerApp.db"
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }
    }
}