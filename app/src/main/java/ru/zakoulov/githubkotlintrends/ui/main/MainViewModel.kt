package ru.zakoulov.githubkotlintrends.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.zakoulov.githubkotlintrends.data.Repo
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

    val viewingFragment = MutableLiveData<ViewingFragment>(ViewingFragment.ReposList)

    fun openReposViewer(repo: Repo) {
        Log.d(TAG, "openReposViewer() called with: repo = $repo")
        viewingFragment.value = ViewingFragment.RepoViewer(repo)
    }

    fun openReposList() {
        viewingFragment.value = ViewingFragment.ReposList
    }

    init {
        fetchRepos(language = LANGUAGE_ON_STARTUP, since = SINCE_ON_STARTUP)
    }

    private fun fetchRepos(language: ReposRepository.Language, since: ReposRepository.Since) {
        reposRepository.fetchTrendRepositories(language, since)
    }

    sealed class ViewingFragment {
        object ReposList : ViewingFragment()
        class RepoViewer(val repo: Repo) : ViewingFragment()
    }

    companion object {
        private const val TAG = "MainViewModel"
        val LANGUAGE_ON_STARTUP = ReposRepository.Language.KOTLIN
        val SINCE_ON_STARTUP = ReposRepository.Since.WEEKLY
    }
}
