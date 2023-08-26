package com.pedro.solutions.dialysisnotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pedro.solutions.dialysisnotes.model.Dialysis
import com.pedro.solutions.dialysisnotes.model.DialysisDAO

@Database(entities = [Dialysis::class], version = 1)
abstract class DialysisDatabase : RoomDatabase() {

    abstract fun dialysisDao(): DialysisDAO
    companion object {
        @Volatile
        private var INSTANCE: DialysisDatabase? = null

        fun getDatabase(context: Context): DialysisDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, DialysisDatabase::class.java, "dialysis_database").build()
                INSTANCE = instance

                instance
            }
        }
    }
}