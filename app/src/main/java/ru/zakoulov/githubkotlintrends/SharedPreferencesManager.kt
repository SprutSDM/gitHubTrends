package ru.zakoulov.githubkotlintrends

import android.content.SharedPreferences
import ru.zakoulov.githubkotlintrends.data.ReposRepository

class SharedPreferencesManager(private val sharedPreferences: SharedPreferences) {
    fun saveSelectedLanguage(language: ReposRepository.Language) {
        with(sharedPreferences.edit()) {
            putString(SELECTED_LANGUAGE_KEY, language.name)
            commit()
        }
    }

    fun saveSelectedSince(since: ReposRepository.Since) {
        with(sharedPreferences.edit()) {
            putString(SELECTED_SINCE_KEY, since.name)
            commit()
        }
    }

    fun loadSelectedLanguage(): ReposRepository.Language {
        val storedValue = sharedPreferences.getString(SELECTED_LANGUAGE_KEY, DEFAULT_LANGUAGE.name)
        return storedValue?.let {
            ReposRepository.Language.valueOf(it)
        } ?: DEFAULT_LANGUAGE
    }

    fun loadSelectedSince(): ReposRepository.Since {
        val storedValue = sharedPreferences.getString(SELECTED_SINCE_KEY, DEFAULT_SINCE.name)
        return storedValue?.let {
            ReposRepository.Since.valueOf(it)
        } ?: DEFAULT_SINCE
    }

    companion object {
        private const val SELECTED_LANGUAGE_KEY = "selected_language"
        private const val SELECTED_SINCE_KEY = "selected_since_key"

        private val DEFAULT_LANGUAGE = ReposRepository.Language.KOTLIN
        private val DEFAULT_SINCE = ReposRepository.Since.WEEKLY
    }
}
