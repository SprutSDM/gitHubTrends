package ru.zakoulov.githubkotlintrends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import ru.zakoulov.githubkotlintrends.data.Repo
import ru.zakoulov.githubkotlintrends.ui.repos.ReposFragment
import ru.zakoulov.githubkotlintrends.ui.main.MainViewModel
import ru.zakoulov.githubkotlintrends.ui.main.ViewModelFactory
import ru.zakoulov.githubkotlintrends.ui.viewer.RepoViewerFragment

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getViewModelFactory(getApp().reposRepository)
    }

    private fun navigateToRepoViewer(repo: Repo) {
        val docsViewFragment = RepoViewerFragment.newInstance()
        val bundle = Bundle()
        bundle.putString(RepoViewerFragment.KEY_URL_FOR_OPEN, repo.url)
        bundle.putString(RepoViewerFragment.KEY_REPO_TITLE, repo.name)
        docsViewFragment.arguments = bundle
        navigateTo(docsViewFragment, RepoViewerFragment.TAG)
    }

    private fun navigateTo(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(tag)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ReposFragment.newInstance())
                .commitNow()
        }
        mainViewModel.viewingFragment.observe(this) {
            when (it) {
                is MainViewModel.ViewingFragment.ReposList -> {
                    navigateTo(ReposFragment.newInstance(), ReposFragment.TAG)
                }
                is MainViewModel.ViewingFragment.RepoViewer -> {
                    navigateToRepoViewer(it.repo)
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
