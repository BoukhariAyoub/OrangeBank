package com.boukhari.orangebank.data.local

import androidx.room.*

@Entity(tableName = "repo")
data class RepoDto(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "fullName") val fullName: String,
    @ColumnInfo(name = "forks") val forks: Int = 0,
    @ColumnInfo(name = "openIssues") val openIssues: Int = 0,
    @ColumnInfo(name = "watchers") val watchers: Int = 0,
    @ColumnInfo(name = "description") val description: String? = null
)