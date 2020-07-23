package ru.zakoulov.githubkotlintrends.ui.main

import androidx.lifecycle.ViewModel
import ru.zakoulov.githubkotlintrends.data.ReposRepository

class MainViewModel(private val reposRepository: ReposRepository) : ViewModel() {

    val repos = reposRepository.repos
    var selectedLanguage = LANGUAGE_ON_STARTUP
        set(value) {
            field = value
            fetchRepos(language = value, since = selectedSince)
        }
    var selectedSince = SINCE_ON_STARTUP
        set(value) {
            field = value
            fetchRepos(language = selectedLanguage, since = field)
        }

    init {
        fetchRepos(language = LANGUAGE_ON_STARTUP, since = SINCE_ON_STARTUP)
    }

    private fun fetchRepos(language: ReposRepository.Language, since: ReposRepository.Since) {
        reposRepository.fetchTrendRepositories(language, since)
    }

    companion object {
        val LANGUAGE_ON_STARTUP = ReposRepository.Language.KOTLIN
        val SINCE_ON_STARTUP = ReposRepository.Since.WEEKLY
    }
}
