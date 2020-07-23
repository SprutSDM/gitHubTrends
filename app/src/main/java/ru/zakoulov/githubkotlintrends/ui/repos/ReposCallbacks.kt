package ru.zakoulov.githubkotlintrends.ui.repos

import ru.zakoulov.githubkotlintrends.data.Repo

interface ReposCallbacks {
    fun onClick(repo: Repo)
}
