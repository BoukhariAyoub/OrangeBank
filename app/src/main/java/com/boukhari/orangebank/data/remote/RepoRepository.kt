package com.boukhari.orangebank.data.remote

import com.boukhari.orangebank.data.local.RepoDao
import com.boukhari.orangebank.data.local.RepoDto
import com.boukhari.orangebank.data.remote.model.RepoResponse
import com.boukhari.orangebank.domain.RepoRepository
import com.boukhari.orangebank.domain.model.Repo

class ApiRepositoryImpl(
    private val apiService: RetrofitService,
    private val localService: RepoDao
) : RepoRepository {

    override suspend fun getRepos(): List<Repo> {
        return localService.getAll().map { it.toDomain() }
    }

    override suspend fun downloadRepos() {
        val repoList = apiService.getRepos()
        localService.insertAll(repoList.map { it.toDto() })
    }

    override suspend fun getRepoById(id: Int): Repo? {
        return localService.getById(id)?.toDomain()
    }

    private fun RepoResponse.toDto() = RepoDto(
        id = id ?: throw IllegalStateException("Error while parsing : Missing field Id"),
        fullName = fullName
            ?: throw IllegalStateException("Error while parsing : Missing field Name for id:$id"),
        forks = forks ?: 0,
        openIssues = openIssues ?: 0,
        watchers = watchers ?: 0,
        description = description
    )

    private fun RepoDto.toDomain() = Repo(
        id = id,
        fullName = fullName,
        forks = forks,
        openIssues = openIssues,
        watchers = watchers,
        description = description
    )
}