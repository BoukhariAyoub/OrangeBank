package com.boukhari.orangebank.domain.repository

import com.boukhari.orangebank.domain.repository.model.Repo

interface RepoRepository {
    suspend fun getRepos(): List<Repo>
}