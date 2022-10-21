package com.boukhari.orangebank.domain

import com.boukhari.orangebank.domain.model.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

interface GetRepoUseCase {
    fun getRepos(shouldRefresh: Boolean = false): Flow<Resource<List<Repo>>>
    fun getRepoById(id: Int): Flow<Resource<Repo>>
}

class GetRepoUseCaseImpl(private val repository: RepoRepository) : GetRepoUseCase {

    override fun getRepos(shouldRefresh: Boolean): Flow<Resource<List<Repo>>> = flow {
        try {
            emit(Resource.Loading())
            if (shouldRefresh) {
                repository.downloadRepos()
            }
            val repos = repository.getRepos()
            emit(Resource.Success(repos))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected Http error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Throwable) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error"))
        }
    }

    override fun getRepoById(id: Int): Flow<Resource<Repo>> = flow {
        val repo = repository.getRepoById(id)
        if (repo == null) {
            emit(Resource.Error("Repository not found by the id provided : $id"))
        } else {
            emit(Resource.Success(repo))
        }
    }
}