package ru.zakoulov.githubkotlintrends.ui.main

import androidx.lifecycle.ViewModel
import ru.zakoulov.githubkotlintrends.data.ReposRepository

class MainViewModel(private val reposRepository: ReposRepository) : ViewModel() {

    val repos = reposRepository.repos

    init {
        reposRepository.fetchTrendRepositories(LANGUAGE_ON_STARTUP, SINCE_ON_STARTUP)
    }

    companion object {
        val LANGUAGE_ON_STARTUP = ReposRepository.Language.KOTLIN
        val SINCE_ON_STARTUP = ReposRepository.Since.WEEKLY
    }
}
