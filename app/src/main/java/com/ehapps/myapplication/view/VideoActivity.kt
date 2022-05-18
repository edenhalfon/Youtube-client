package com.ehapps.myapplication.view

import android.webkit.WebResourceRequest
import android.webkit.WebView


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.ehapps.myapplication.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initVideo()
    }

    private fun initVideo() {
        intent.getStringExtra(VIDEO_URL)?.also {
            binding.videoWebView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    return false
                }
            }

            binding.videoWebView.settings.javaScriptEnabled = true
            binding.videoWebView.loadUrl(it)
        }
    }

    companion object {
        private const val VIDEO_URL = "video_url"

        fun getIntent(context: Context, videoUrl: String) =
            Intent(context, VideoActivity::class.java).also {
                it.putExtra(VIDEO_URL, videoUrl)
            }
    }
}