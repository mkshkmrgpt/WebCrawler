package com.mukesh.webcrawler

import org.jsoup.select.Elements
import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface LinkCrawler {
    fun getAllLinks(site: String): MutableList<Link>
}

interface MediaCrawler {
    fun getAllMedia(site: String): MutableList<Media>
}

object BasicLinkCrawler : LinkCrawler {

    private val logger: Logger = LoggerFactory.getLogger(BasicLinkCrawler::class.java)

    override fun getAllLinks(link: String): MutableList<Link> {
        val documentSelector = "a[href]"
        val elements: Elements? = CrawlerConnection.getElements(link, documentSelector)
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
}

object BasicMediaCrawler : MediaCrawler {

    private val logger: Logger = LoggerFactory.getLogger(BasicMediaCrawler::class.java)

    override fun getAllMedia(link: String): MutableList<Media> {
        val documentSelector = "[src]"
        val elements: Elements? = CrawlerConnection.getElements(link, documentSelector)
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
}