package ru.zakoulov.githubkotlintrends

import android.app.Activity
import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.zakoulov.githubkotlintrends.data.ReposRepository
import ru.zakoulov.githubkotlintrends.data.source.github.GitHubApi
import ru.zakoulov.githubkotlintrends.data.source.github.GitHubRepoDataSource
import ru.zakoulov.githubkotlintrends.data.source.github.GitHubRepoMapper
import ru.zakoulov.githubkotlintrends.data.source.github.GitHubReposListMapper

class App : Application() {
    private lateinit var job: Job
    lateinit var reposRepository: ReposRepository

    override fun onCreate() {
        super.onCreate()

        val gitHubApi = createGitHubApi()
        val gitHubDataSource = GitHubRepoDataSource(
            gitHubApi = gitHubApi,
            reposListMapper = GitHubReposListMapper(
                repoMapper = GitHubRepoMapper()
            )
        )

        job = Job()
        val coroutineContext = job + Dispatchers.IO
        val scope = CoroutineScope(coroutineContext)

        reposRepository = ReposRepository(gitHubDataSource, scope)
    }

    private fun createGitHubApi(): GitHubApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(GitHubApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GitHubApi::class.java)
    }

    companion object {
        private const val TAG = "App"
    }
}

fun Activity.getApp() = this.application as App
