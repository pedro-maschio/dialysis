package com.pedro.solutions.dialysisnotes.data.dialysis

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pedro.solutions.dialysisnotes.data.pdf.PDF
import com.pedro.solutions.dialysisnotes.data.pdf.pdfDao

@Database(entities = [Dialysis::class, PDF::class], version = 1, exportSchema = false)
abstract class DialysisDatabase : RoomDatabase() {

    abstract fun dialysisDao(): DialysisDAO

    abstract fun pdfDao(): pdfDao
}