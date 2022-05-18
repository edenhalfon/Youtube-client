package com.ehapps.myapplication.manager

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection

object WebpageManager {

    fun fetchWebPageHtml(url: String): String {
        val siteUrl = URL(url)
        val con: URLConnection = siteUrl.openConnection()
        con.setRequestProperty("User-Agent", "Mozilla/5.0")
        val inputBuffer = BufferedReader(InputStreamReader(con.getInputStream(), "UTF-8"))
        var input: String?
        val stringBuffer = StringBuffer()
        while (inputBuffer.readLine().also { input = it } != null) {
            stringBuffer.append(input)
        }
        inputBuffer.close()
        return stringBuffer.toString()
    }
}