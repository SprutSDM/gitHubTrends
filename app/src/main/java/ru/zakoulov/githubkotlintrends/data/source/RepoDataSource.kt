package ru.zakoulov.githubkotlintrends.data.source

import ru.zakoulov.githubkotlintrends.data.DataResult
import ru.zakoulov.githubkotlintrends.data.ReposList

interface RepoDataSource {
    suspend fun getTrendRepositories(language: String?, since: String?): DataResult<ReposList>
}
