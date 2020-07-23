package ru.zakoulov.githubkotlintrends.ui.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import ru.zakoulov.githubkotlintrends.R

class RepoViewerFragment : Fragment() {
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.repos_viewer_fragment, container, false)
        with (root) {
            webView = findViewById(R.id.web_view)
            progressBar = findViewById(R.id.progress_bar)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString(KEY_URL_FOR_OPEN)!!
        val docTitle = arguments?.getString(KEY_REPO_TITLE)!!
        webView.webViewClient = FileWebViewClient(object : PageLoadingCallbacks {
            override fun pageFinished() {
                showLoaded()
            }
        })
        webView.settings.apply {
            useWideViewPort = true
            loadWithOverviewMode = true
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
        }
        loadUrl(url)
        activity?.title = docTitle
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webView.stopLoading()
    }

    fun showLoaded() {
        progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun loadUrl(url: String) {
        showLoading()
        webView.loadUrl(url)
    }

    companion object {
        const val TAG = "WebsiteViewerFragment"
        const val KEY_URL_FOR_OPEN = "key_url_for_open"
        const val KEY_REPO_TITLE = "key_repo_title"

        fun newInstance() = RepoViewerFragment()
    }
}
