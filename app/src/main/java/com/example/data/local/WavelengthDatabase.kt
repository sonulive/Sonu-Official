package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.Dilemma
import com.example.data.model.UserProfile

@Database(
    entities = [Dilemma::class, UserProfile::class],
    version = 1,
    exportSchema = false
)
abstract class WavelengthDatabase : RoomDatabase() {
    abstract fun wavelengthDao(): WavelengthDao

    companion object {
        @Volatile
        private var INSTANCE: WavelengthDatabase? = null

        fun getInstance(context: Context): WavelengthDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WavelengthDatabase::class.java,
                    "wavelength_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
