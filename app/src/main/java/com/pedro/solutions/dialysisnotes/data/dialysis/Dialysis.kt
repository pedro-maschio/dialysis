package com.pedro.solutions.dialysisnotes.data.dialysis

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dialysis(
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
    @ColumnInfo(name = "initial_uf") val initialUf: Int,
    @ColumnInfo(name = "final_uf") val finalUf: Int,
    @ColumnInfo(name = "notes") val notes: String,
    @PrimaryKey(autoGenerate = true) val id: Int?,
)