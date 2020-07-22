package ru.zakoulov.githubkotlintrends.data.source.github

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("repositories/")
    suspend fun getTrendRepositories(
        @Query("language") language: String? = null,
        @Query("since") since: String? = null
    ): Response<GitHubReposList>

    companion object {
        const val BASE_URL = "https://ghapi.huchen.dev/"
    }
}
