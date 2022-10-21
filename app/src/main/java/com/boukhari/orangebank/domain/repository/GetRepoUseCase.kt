package com.boukhari.orangebank.domain.repository

import com.boukhari.orangebank.domain.repository.model.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

interface GetRepoUseCase {
    fun getRepos(): Flow<Resource<List<Repo>>>
}

class GetRepoUseCaseImpl(private val repository: RepoRepository) : GetRepoUseCase {
    override fun getRepos(): Flow<Resource<List<Repo>>> = flow {
        try {
            emit(Resource.Loading())
            val repos = repository.getRepos()
            emit(Resource.Success(repos))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Throwable) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error"))
        }
    }
}