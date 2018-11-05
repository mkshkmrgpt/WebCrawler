package com.mukesh.webcrawler.service.impl

import com.mukesh.webcrawler.connection.CrawlerConnection
import com.mukesh.webcrawler.data.Link
import com.mukesh.webcrawler.service.LinkCrawler
import org.jsoup.nodes.Document
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component("basicLink")
class BasicLinkCrawler : LinkCrawler {

    private val logger: Logger = LoggerFactory.getLogger(BasicLinkCrawler::class.java)
    private val linkSelector = "a[href]"

    override fun getAllLinks(link: String): Link {
        val document: Document = CrawlerConnection.getDocument(link) as Document
        val elements = document.select(linkSelector)
        val title = document.title()
        logger.debug("Getting all links for site $link")
        return Link(title, elements
                .map { it.attr("abs:href") }
                .filter { !it.isNullOrBlank() }
                .toMutableList())
    }
}