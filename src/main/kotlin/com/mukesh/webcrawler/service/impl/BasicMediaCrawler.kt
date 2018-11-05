package com.mukesh.webcrawler.service.impl

import com.mukesh.webcrawler.connection.CrawlerConnection
import com.mukesh.webcrawler.data.Media
import com.mukesh.webcrawler.service.MediaCrawler
import org.jsoup.nodes.Document
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component("basicMedia")
class BasicMediaCrawler : MediaCrawler {

    private val logger: Logger = LoggerFactory.getLogger(BasicMediaCrawler::class.java)
    private val mediaSelector = "[src]"

    override fun getAllMedia(link: String): Media {
        val document: Document = CrawlerConnection.getDocument(link) as Document
        val elements = document.select(mediaSelector)
        val title = document.title()
        logger.debug("Getting media files for site $link")
        return Media(title, elements.map { it.attr("abs:src") }
                .filter { !it.isNullOrBlank() }
                .toMutableList())
    }
}