package com.boukhari.orangebank.data.remote

import com.boukhari.orangebank.data.remote.model.RepoResponse
import retrofit2.http.GET

const val API_BASE_URL = "https://api.github.com"
interface RetrofitService {
    @GET("/orgs/jetbrains/repos")
    suspend fun getRepos(): List<RepoResponse>
}











