package ru.zakoulov.githubkotlintrends.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.zakoulov.githubkotlintrends.data.ReposRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val reposRepository: ReposRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(reposRepository) as T
    }

    companion object {
        private var instance: ViewModelFactory? = null

        fun getViewModelFactory(reposRepository: ReposRepository): ViewModelFactory {
            instance?.let {
                return it
            }
            instance = ViewModelFactory(reposRepository)
            return instance!!
        }
    }
}
