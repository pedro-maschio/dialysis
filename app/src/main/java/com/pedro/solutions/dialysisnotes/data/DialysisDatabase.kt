package com.pedro.solutions.dialysisnotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Dialysis::class, PDF::class], version = 1)
abstract class DialysisDatabase : RoomDatabase() {

    abstract fun dialysisDao(): DialysisDAO

    abstract fun pdfDao(): pdfDao

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