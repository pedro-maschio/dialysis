package com.pedro.solutions.dialysisnotes.model

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DialysisDAO {
    @Query("SELECT * FROM Dialysis ORDER BY created_at DESC")
    fun getAllDialysis(): Flow<List<Dialysis>>
}