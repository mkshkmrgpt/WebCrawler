package com.mukesh.webcrawler

import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CrawlerService {

    val logger: Logger = LoggerFactory.getLogger(CrawlerService::class.java)

    fun getAllLinks(link: String): MutableList<Link> {
        val documentSelector = "a[href]"
        val elements: Elements? = getElements(link, documentSelector)
        val links: MutableList<Link> = mutableListOf()
        elements?.forEach {
            val link = Link(it.text(), it.attr("abs:href"))
            links.add(link)
            logger.debug("Link", link)
        }
        return links
    }

    fun getAllMedia(link: String): MutableList<Media> {
        val documentSelector = "[src]"
        val elements: Elements? = getElements(link, documentSelector)
        val mediaList = mutableListOf<Media>()
        elements?.forEach {
            if (it.tagName() == "img") {
                val media = Media(it.attr("alt"), it.attr("abs:src"))
                mediaList.add(media)
                logger.debug("Media", media)
            }
        }
        return mediaList
    }

    internal fun getElements(link: String, documentSelector: String): Elements? {
        val connection = Jsoup.connect(link)
        val document = connection.get()
        var elements: Elements? = null
        if (connection.response().statusCode() == 200) {
            elements = document.select(documentSelector)
        }
        return elements
    }
}
