package ru.zakoulov.githubkotlintrends.data.source.github

data class GitHubRepo(
    val author: String,
    val name: String,
    val avatar: String,
    val description: String,
    val url: String,
    val language: String?,
    val languageColor: String?,
    val stars: Int,
    val forks: Int,
    val currentPeriodStars: Int
)
