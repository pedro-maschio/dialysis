package com.pedro.solutions.dialysisnotes.model

import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface DialysisDAO {
    @Query("SELECT * FROM Dialysis ORDER BY created_at DESC")
    fun getAllDialysis(): Flow<List<Dialysis>>
}