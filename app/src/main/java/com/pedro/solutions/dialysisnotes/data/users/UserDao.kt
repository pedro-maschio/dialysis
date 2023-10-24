package com.pedro.solutions.dialysisnotes.data.users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: User)

    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    suspend fun findUserByEmailAndPassword(email: String, password: String): User?
}