package ru.zakoulov.githubkotlintrends.data

data class ReposList(
    val repos: List<Repo>
)

data class Repo(
    val author: String,
    val name: String,
    val avatar: String,
    val description: String,
    val url: String,
    val language: String,
    val languageColor: String,
    val stars: Int,
    val forks: Int,
    val currentPeriodStars: Int
)
