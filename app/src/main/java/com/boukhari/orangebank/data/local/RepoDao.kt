package com.boukhari.orangebank.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface RepoDao {

    @Query("SELECT * FROM repo")
    suspend fun getAll(): List<RepoDto>

    @Query("SELECT * FROM repo WHERE id = :id")
    suspend fun getById(id: Int): RepoDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoDto>)
}