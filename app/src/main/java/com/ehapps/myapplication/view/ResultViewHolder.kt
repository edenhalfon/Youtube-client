package com.ehapps.myapplication.view

import androidx.recyclerview.widget.RecyclerView
import com.ehapps.myapplication.databinding.VideoResultItemBinding

class ResultViewHolder(val binding: VideoResultItemBinding, val listener: SearchResultsAdapter.Listener): RecyclerView.ViewHolder(binding.root) {

    var videoUrl: String? = null

    init {
        binding.coverImage.setOnClickListener { _ ->
            videoUrl?.also {
                listener.onItemClicked(it)
            }
        }
    }
}