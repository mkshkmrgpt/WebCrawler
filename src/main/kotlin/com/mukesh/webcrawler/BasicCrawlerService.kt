package com.mukesh.webcrawler

import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BasicCrawlerService : LinkCrawler, MediaCrawler{

    val logger: Logger = LoggerFactory.getLogger(BasicCrawlerService::class.java)

    override fun getAllLinks(link: String): MutableList<Link> {
        val documentSelector = "a[href]"
        val elements: Elements? = getElements(link, documentSelector)
        val links: MutableList<Link> = mutableListOf()
        try {
            elements?.forEach {
                val link = Link(it.text(), it.attr("abs:href"))
                links.add(link)
                logger.debug("Link", link)
            }
        } catch (ex: Exception) {
            //parsing exception can be ignored
        }
        return links
    }

    override fun getAllMedia(link: String): MutableList<Media> {
        val documentSelector = "[src]"
        val elements: Elements? = getElements(link, documentSelector)
        val mediaList = mutableListOf<Media>()
        try {
            elements?.forEach {
                if (it.tagName() == "img") {
                    val media = Media(it.attr("alt"), it.attr("abs:src"))
                    mediaList.add(media)
                    logger.debug("Media", media)
                }
            }
        } catch (ex: Exception) {
            //parsing exception can be ignored
        }
        return mediaList
    }

    fun getElements(link: String, documentSelector: String): Elements? {
        var elements: Elements? = null
        try {
            val connection = Jsoup.connect(link)
            val document = connection.get()
            if (connection.response().statusCode() == 200) {
                elements = document.select(documentSelector)
            }
        } catch (ex: Exception) {
            logger.error("Something went wrong with $link")
            throw CrawlerException("Unable to crawl $link")
        }
        return elements
    }
}
