package ru.zakoulov.githubkotlintrends.data.source.github

import ru.zakoulov.githubkotlintrends.data.Mapper
import ru.zakoulov.githubkotlintrends.data.Repo
import ru.zakoulov.githubkotlintrends.data.ReposList

class GitHubReposListMapper(
    private val repoMapper: GitHubRepoMapper
) : Mapper<List<GitHubRepo>, ReposList> {
    override fun map(input: List<GitHubRepo>): ReposList {
        return ReposList(input.map { repo ->
            repoMapper.map(repo)
        })
    }
}

class GitHubRepoMapper : Mapper<GitHubRepo, Repo> {
    override fun map(input: GitHubRepo): Repo {
        return Repo(
            author = input.author,
            name = input.name,
            avatar = input.avatar,
            description = input.description,
            url = input.url,
            currentPeriodStars = input.currentPeriodStars,
            forks = input.forks,
            language = input.language ?: "",
            languageColor = input.languageColor ?: "",
            stars = input.starts
        )
    }
}
