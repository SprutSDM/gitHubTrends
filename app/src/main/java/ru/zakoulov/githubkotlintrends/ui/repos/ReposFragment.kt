package ru.zakoulov.githubkotlintrends.ui.repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.zakoulov.githubkotlintrends.App
import ru.zakoulov.githubkotlintrends.R
import ru.zakoulov.githubkotlintrends.data.DataResult
import ru.zakoulov.githubkotlintrends.data.ReposList
import ru.zakoulov.githubkotlintrends.data.ReposRepository
import ru.zakoulov.githubkotlintrends.ui.main.MainViewModel
import ru.zakoulov.githubkotlintrends.ui.main.ViewModelFactory

class ReposFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels {
        ViewModelFactory.getViewModelFactory(
            (requireActivity().application as App).reposRepository
        )
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewManager: LinearLayoutManager
    private lateinit var recyclerViewAdapter: ReposViewAdapter
    private lateinit var intervalsSpinner: Spinner
    private lateinit var spinnerAdapter: SpinnerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.repos_fragment, container, false).apply {
            recyclerView = findViewById(R.id.recycler_view)
            intervalsSpinner = findViewById(R.id.intervals_spinner)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupSpinner()

        viewModel.repos.observe(viewLifecycleOwner, Observer { reposDataResult ->
            when {
                reposDataResult is DataResult.Success -> {
                    recyclerViewAdapter.reposList = reposDataResult.data!!
                }
            }
        })
    }

    private fun setupRecycler() {
        recyclerViewManager = LinearLayoutManager(this.context)
        recyclerViewAdapter = ReposViewAdapter(ReposList(emptyList()))
        recyclerView.apply {
            layoutManager = recyclerViewManager
            adapter = recyclerViewAdapter
        }
    }

    private fun setupSpinner() {
        val periods = ReposRepository.Since.values().map {
            getString(resources.getIdentifier(
                it.value,
                "string",
                requireContext().packageName
            ))
        }
        spinnerAdapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            periods
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        intervalsSpinner.apply {
            adapter = spinnerAdapter
            setSelection(viewModel.selectedSince.ordinal)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.selectedSince = ReposRepository.Since.values()[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
        }
    }

    companion object {
        private const val TAG = "ReposFragment"

        fun newInstance() = ReposFragment()
    }
}
