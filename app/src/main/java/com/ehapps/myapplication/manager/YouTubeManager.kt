package com.ehapps.myapplication.manager

import android.util.Log
import com.ehapps.myapplication.model.YouTubeVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.HashSet

object YouTubeManager {

    private const val URL_TEMPLATE = "https://www.youtube.com/results?search_query="
    private const val VIDEO_URL_TEMPLATE = "https://www.youtube.com/watch?v="
    private const val VIDEO_HTML_TEMPLATE = "videoId\":\""
    private const val THUMBNAIL_HTML_TEMPLATE = "\"url\":\""

    suspend fun getVideosFromQuery(query: String): List<YouTubeVideo> {
        return withContext(Dispatchers.IO) {
            val videosList = LinkedList<YouTubeVideo>()
            val videosSet = HashSet<String>()
            var html = WebpageManager.fetchWebPageHtml(URL_TEMPLATE + query)
            Log.d("EDEN", html)
            while (html.contains(VIDEO_HTML_TEMPLATE)) {
                val firstStartIndex = html.indexOf(VIDEO_HTML_TEMPLATE) + VIDEO_HTML_TEMPLATE.length
                val idEndIndex = html.indexOf("\"", firstStartIndex)
                val id = html.substring(firstStartIndex, idEndIndex)
                if (!videosSet.contains(id)) {
                    val imageUrlStart = html.indexOf(THUMBNAIL_HTML_TEMPLATE, idEndIndex) + THUMBNAIL_HTML_TEMPLATE.length
                    val imageUrl = html.substring(imageUrlStart, html.indexOf("\"", imageUrlStart))
                    Log.d("EDEN", "getVideosFromQuery: $id, $imageUrl")
                    videosList.add(YouTubeVideo(VIDEO_URL_TEMPLATE + id, imageUrl))
                    videosSet.add(id)
                }
                html = html.substring(idEndIndex)
            }
            Log.d("EDEN", "getVideosFromQuery: ${videosList.size}")
            videosList
        }

    }
}