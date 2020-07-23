package ru.zakoulov.githubkotlintrends.data

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.zakoulov.githubkotlintrends.data.source.RepoDataSource

class ReposRepository(
    private val repoDataSource: RepoDataSource,
    private val coroutineScope: CoroutineScope
) {
    val repos = MutableLiveData<DataResult<ReposList>>()

    fun fetchTrendRepositories(language: Language, since: Since) {
        if (repos.value?.isLoading() == true) {
            return
        }
        repos.value = DataResult.Loading()

        coroutineScope.launch {
            val response: DataResult<ReposList> = try {
                repoDataSource.getTrendRepositories(
                    language = language.value,
                    since = since.value
                )
            } catch (error: Throwable) {
                DataResult.Fail("Error when fetching repos")
            }
            repos.postValue(response)
        }
    }

    enum class Since(val value: String) {
        DAILY("daily"),
        WEEKLY("weekly"),
        MONTHLY("monthly")
    }

    enum class Language(val value: String) {
        KOTLIN("kotlin")
    }
}
