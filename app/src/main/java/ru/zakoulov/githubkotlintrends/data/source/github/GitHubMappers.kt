package ru.zakoulov.githubkotlintrends.data.source.github

import ru.zakoulov.githubkotlintrends.data.Mapper
import ru.zakoulov.githubkotlintrends.data.Repo
import ru.zakoulov.githubkotlintrends.data.ReposList

class GitHubReposListMapper(
    private val repoMapper: GitHubRepoMapper
) : Mapper<GitHubReposList, ReposList> {
    override fun map(input: GitHubReposList): ReposList {
        return ReposList(input.repos.map { repo ->
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
            currentPeriodStars = input.currentPeriodStars,
            forks = input.forks,
            language = input.language,
            languageColor = input.languageColor,
            stars = input.starts
        )
    }
}
