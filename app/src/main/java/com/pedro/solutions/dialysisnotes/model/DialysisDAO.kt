package com.pedro.solutions.dialysisnotes.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DialysisDAO {
    @Query("SELECT * FROM Dialysis ORDER BY created_at DESC")
    fun getAllDialysis(): Flow<List<Dialysis>>

    @Query("SELECT * FROM Dialysis WHERE id = :id")
    fun findDialysisById(id: Int?): Flow<Dialysis?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDialysis(dialysis: Dialysis)

    @Update
    suspend fun updateDialysis(dialysis: Dialysis)

    @Delete
    suspend fun deleteDialysis(dialysis: Dialysis)
}