package com.pedro.solutions.dialysisnotes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dialysis(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
    @ColumnInfo(name = "initial_uf") val initialUf: Int,
    @ColumnInfo(name = "final_uf") val finalUf: Int,
    @ColumnInfo(name = "notes") val notes: String
)