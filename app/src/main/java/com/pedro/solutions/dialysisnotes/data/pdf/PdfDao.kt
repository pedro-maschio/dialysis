package com.pedro.solutions.dialysisnotes.data.pdf

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface pdfDao {
    @Query("SELECT * FROM PDF ORDER BY created_at DESC")
    fun getAllPDFs(): Flow<List<PDF>>

    @Query("SELECT * FROM PDF WHERE id = :id")
    suspend fun findPDFById(id: Int): PDF?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPDF(pdf: PDF)

    @Update
    suspend fun updatePDF(pdf: PDF)

    @Query("DELETE FROM PDF WHERE id = :id")
    suspend fun deletePDF(id: Int)
}