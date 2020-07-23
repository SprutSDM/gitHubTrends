package ru.zakoulov.githubkotlintrends.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.zakoulov.githubkotlintrends.data.Repo
import ru.zakoulov.githubkotlintrends.data.ReposRepository

class MainViewModel(private val reposRepository: ReposRepository) : ViewModel() {

    val repos = reposRepository.repos
    val currentSince = reposRepository.currentSince
    val currentLanguage = reposRepository.currentLanguage

    val viewingFragment = MutableLiveData<ViewingFragment>(ViewingFragment.ReposList)

    fun openReposViewer(repo: Repo) {
        Log.d(TAG, "openReposViewer() called with: repo = $repo")
        viewingFragment.value = ViewingFragment.RepoViewer(repo)
    }

    fun openReposList() {
        viewingFragment.value = ViewingFragment.ReposList
    }

    init {
        currentSince.observeForever {
            fetchRepos(language = currentLanguage.value!!, since = it)
        }
        currentLanguage.observeForever {
            fetchRepos(language = it, since = currentSince.value!!)
        }
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
