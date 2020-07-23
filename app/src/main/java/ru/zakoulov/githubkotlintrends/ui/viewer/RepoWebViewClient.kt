package ru.zakoulov.githubkotlintrends.ui.viewer

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class FileWebViewClient(private val callbacks: PageLoadingCallbacks) : WebViewClient() {

    override fun onPageCommitVisible(view: WebView?, url: String?) {
        callbacks.pageFinished()
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
        return false
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        return false
    }
}
