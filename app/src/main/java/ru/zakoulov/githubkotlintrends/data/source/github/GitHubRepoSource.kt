package ru.zakoulov.githubkotlintrends.data.source.github

import ru.zakoulov.githubkotlintrends.data.DataResult
import ru.zakoulov.githubkotlintrends.data.ReposList
import ru.zakoulov.githubkotlintrends.data.source.RepoDataSource

class GitHubRepoDataSource(
    private val gitHubApi: GitHubApi,
    private val reposListMapper: GitHubReposListMapper
) : RepoDataSource {

    override suspend fun getTrendRepositories(language: String?, since: String?): DataResult<ReposList> {
        val response = gitHubApi.getTrendRepositories(language, since)
        return if (response.isSuccessful) {
            DataResult.Success(reposListMapper.map(response.body()!!))
        } else {
            DataResult.Fail(response.message())
        }
    }
}
