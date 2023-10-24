package com.pedro.solutions.dialysisnotes.data.pdf

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PDF(
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
    @ColumnInfo(name = "patient_name") val patientName: String,
    @ColumnInfo(name="start_interval") val startInterval: Long,
    @ColumnInfo(name="end_interval") val endInterval: Long,
    @ColumnInfo(name="file_directory") val fileDirectory: String,
    @PrimaryKey(autoGenerate = true) val id: Int?,
)