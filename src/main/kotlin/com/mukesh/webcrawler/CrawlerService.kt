package com.mukesh.webcrawler

import org.jsoup.Jsoup
import org.jsoup.select.Elements

class CrawlerService {

    fun getAllLinks(link: String): MutableList<Link> {
        val documentSelector = "a[href]"
        var elements: Elements? = getElements(link, documentSelector)
        var links:MutableList<Link> = mutableListOf()
        elements?.forEach {
            val link = Link(it.text(), it.attr("abs:href"))
            links.add(link)
            println(link)
        }
        return links
    }

    fun getAllMedia(link: String): MutableList<Media> {
        val documentSelector = "[src]"
        var elements: Elements? = getElements(link, documentSelector)
        var mediaList = mutableListOf<Media>()
        elements?.forEach {
            if (it.tagName() == "img") {
                val media = Media(it.absUrl("alt"), it.attr("abs:src"))
                mediaList.add(media)
                println(media)
            }
        }
        return mediaList
    }

    private fun getElements(link: String, documentSelector: String): Elements? {
        val connection = Jsoup.connect(link)
        val document = connection.get()
        var elements: Elements? = null
        if (connection.response().statusCode() == 200) {
            elements = document.select(documentSelector)
        }
        return elements
    }
}
