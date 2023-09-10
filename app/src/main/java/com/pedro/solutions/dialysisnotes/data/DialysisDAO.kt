package com.pedro.solutions.dialysisnotes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DialysisDAO {
    @Query("SELECT * FROM Dialysis ORDER BY created_at DESC")
    fun getAllDialysisNewestFirst(): Flow<List<Dialysis>>

    @Query("SELECT created_at FROM Dialysis ORDER BY created_at ASC LIMIT(1)")
    fun getOldestDialysis(): Flow<List<Long>>


    @Query("SELECT * FROM Dialysis WHERE id = :id")
    suspend fun findDialysisById(id: Int): Dialysis?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDialysis(dialysis: Dialysis)

    @Update
    suspend fun updateDialysis(dialysis: Dialysis)

    @Query("DELETE FROM Dialysis WHERE id = :id")
    suspend fun deleteDialysis(id: Int)
}