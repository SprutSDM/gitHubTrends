package ru.zakoulov.githubkotlintrends.ui.repos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.zakoulov.githubkotlintrends.App
import ru.zakoulov.githubkotlintrends.R
import ru.zakoulov.githubkotlintrends.data.DataResult
import ru.zakoulov.githubkotlintrends.data.ReposList
import ru.zakoulov.githubkotlintrends.ui.main.MainViewModel
import ru.zakoulov.githubkotlintrends.ui.main.ViewModelFactory

class ReposFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels {
        ViewModelFactory.getViewModelFactory(
            (requireActivity().application as App).reposRepository
        )
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var viewAdapter: ReposViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.repos_fragment, container, false).apply {
            recyclerView = findViewById(R.id.recycler_view)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewManager = LinearLayoutManager(this.context)
        viewAdapter = ReposViewAdapter(ReposList(emptyList()))
        recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        viewModel.repos.observe(viewLifecycleOwner, Observer { reposDataResult ->
            when {
                reposDataResult is DataResult.Success -> {
                    viewAdapter.reposList = reposDataResult.data!!
                }
            }
        })
    }

    companion object {
        private const val TAG = "ReposFragment"

        fun newInstance() = ReposFragment()
    }
}
