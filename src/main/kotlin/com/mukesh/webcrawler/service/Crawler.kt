package com.mukesh.webcrawler.service

import com.mukesh.webcrawler.data.Link
import com.mukesh.webcrawler.data.Media

interface LinkCrawler {
    fun getAllLinks(site: String): Link
}

interface MediaCrawler {
    fun getAllMedia(site: String): Media
}
