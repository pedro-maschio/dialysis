package com.pedro.solutions.dialysisnotes.data.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
    @ColumnInfo(name="email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @PrimaryKey(autoGenerate = true) val id: Int?
)