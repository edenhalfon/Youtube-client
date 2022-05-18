package com.ehapps.myapplication.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ehapps.myapplication.R
import com.ehapps.myapplication.databinding.ActivityMainBinding
import com.ehapps.myapplication.model.YouTubeVideo
import com.ehapps.myapplication.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), SearchResultsAdapter.Listener {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initUI()
    }

    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.videosStateFlow.collect {
                    onNewItemsReceived(it)
                }
            }
        }
    }

    private fun initUI() {
        binding.search.setOnClickListener {
            binding.loader.visibility = View.VISIBLE
            viewModel.search(binding.searchBox.text.toString())
        }

        if (!this::adapter.isInitialized) {
            adapter = SearchResultsAdapter(listener = this)
        }

        binding.searchResults.adapter = adapter
    }

    private fun onNewItemsReceived(items: List<YouTubeVideo>) {
        binding.loader.visibility = View.GONE
        binding.searchResults.visibility = View.VISIBLE
        adapter.addItems(items)
    }

    override fun onItemClicked(videoUrl: String) {
        startActivity(VideoActivity.getIntent(this, videoUrl))
    }
}