package com.boukhari.orangebank.domain

import com.boukhari.orangebank.domain.model.Repo

interface RepoRepository {
    suspend fun getRepos(): List<Repo>
    suspend fun downloadRepos()
    suspend fun getRepoById(id: Int): Repo?
}