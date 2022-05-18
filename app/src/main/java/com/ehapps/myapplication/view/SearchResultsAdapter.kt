package com.ehapps.myapplication.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ehapps.myapplication.databinding.VideoResultItemBinding
import com.ehapps.myapplication.model.YouTubeVideo
import java.util.*

class SearchResultsAdapter(val items: LinkedList<YouTubeVideo> = LinkedList<YouTubeVideo>(), val listener: Listener) :
    RecyclerView.Adapter<ResultViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VideoResultItemBinding.inflate(inflater, parent, false)
        return ResultViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.videoUrl = items[position].videoUrl
        holder.binding.coverImage.load(items[position].imageUrl)
    }

    override fun getItemCount() = items.size

    fun addItems(newItems: List<YouTubeVideo>) {
        val lastItemCount = items.size
        items.addAll(newItems)
        notifyItemInserted(lastItemCount)
    }

    interface Listener {
        fun onItemClicked(videoUrl: String)
    }
}