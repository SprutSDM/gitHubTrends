package ru.zakoulov.githubkotlintrends.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import ru.zakoulov.githubkotlintrends.App
import ru.zakoulov.githubkotlintrends.R

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels {
        ViewModelFactory.getViewModelFactory((requireActivity().application as App).reposRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.repos.observe(viewLifecycleOwner, Observer { reposDataResult ->
            Log.d(TAG, "onActivityCreated() called with: reposDataResult = $reposDataResult")
        })
    }
    companion object {
        private const val TAG = "MainFragment"

        fun newInstance() = MainFragment()
    }
}
