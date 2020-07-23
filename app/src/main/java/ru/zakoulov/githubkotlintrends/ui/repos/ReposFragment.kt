package ru.zakoulov.githubkotlintrends.ui.repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.zakoulov.githubkotlintrends.App
import ru.zakoulov.githubkotlintrends.R
import ru.zakoulov.githubkotlintrends.data.DataResult
import ru.zakoulov.githubkotlintrends.data.Repo
import ru.zakoulov.githubkotlintrends.data.ReposList
import ru.zakoulov.githubkotlintrends.data.ReposRepository
import ru.zakoulov.githubkotlintrends.ui.main.MainViewModel
import ru.zakoulov.githubkotlintrends.ui.main.ViewModelFactory

class ReposFragment : Fragment(), ReposCallbacks {
    private val viewModel: MainViewModel by activityViewModels {
        ViewModelFactory.getViewModelFactory(
            (requireActivity().application as App).reposRepository
        )
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewManager: LinearLayoutManager
    private lateinit var recyclerViewAdapter: ReposViewAdapter
    private lateinit var intervalSpinner: Spinner
    private lateinit var intervalSpinnerAdapter: SpinnerAdapter
    private lateinit var languageSpinner: Spinner
    private lateinit var languageSpinnerAdapter: SpinnerAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.repos_fragment, container, false).apply {
            recyclerView = findViewById(R.id.recycler_view)
            intervalSpinner = findViewById(R.id.interval_spinner)
            languageSpinner = findViewById(R.id.language_spinner)
            progressBar = findViewById(R.id.progress_bar)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupSinceSpinner()
        setupLanguageSpinner()

        viewModel.repos.observe(viewLifecycleOwner, Observer { reposDataResult ->
            when (reposDataResult) {
                is DataResult.Success -> {
                    recyclerViewAdapter.reposList = reposDataResult.data!!
                    progressBar.visibility = View.GONE
                }
                is DataResult.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        })
        activity?.setTitle(R.string.app_name)
    }

    override fun onClick(repo: Repo) {
        viewModel.openReposViewer(repo)
    }

    private fun setupRecycler() {
        recyclerViewManager = LinearLayoutManager(this.context)
        recyclerViewAdapter = ReposViewAdapter(
            initReposList = ReposList(emptyList()),
            callbacks = this
        )
        recyclerView.apply {
            layoutManager = recyclerViewManager
            adapter = recyclerViewAdapter
        }
    }

    private fun setupSinceSpinner() {
        val periods = ReposRepository.Since.values().map {
            getString(resources.getIdentifier(
                it.value,
                "string",
                requireContext().packageName
            ))
        }
        intervalSpinnerAdapter = ArrayAdapter(
            this.requireContext(),
            R.layout.selected_item_spinner,
            periods
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        intervalSpinner.apply {
            adapter = intervalSpinnerAdapter
            setSelection(viewModel.currentSince.value?.ordinal ?: 0)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.currentSince.value = ReposRepository.Since.values()[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
        }
    }

    private fun setupLanguageSpinner() {
        val languages = ReposRepository.Language.values().map {
            getString(resources.getIdentifier(
                it.value,
                "string",
                requireContext().packageName
            ))
        }
        languageSpinnerAdapter = ArrayAdapter(
            this.requireContext(),
            R.layout.selected_item_spinner,
            languages
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        languageSpinner.apply {
            adapter = languageSpinnerAdapter
            setSelection(viewModel.currentLanguage.value?.ordinal ?: 0)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.currentLanguage.value = ReposRepository.Language.values()[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
        }
    }

    companion object {
        const val TAG = "ReposFragment"

        fun newInstance() = ReposFragment()
    }
}
