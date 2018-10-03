package com.mukesh.webcrawler

import org.jsoup.Jsoup
import org.jsoup.select.Elements

class CrawlerService {

    fun getAllLinks(link: String): Elements? {
        val documentSelector = "a[href]"
        var links: Elements? = getElements(link, documentSelector)
        links?.forEach {
            println("Page ${it.text()} Links ${it.attr("abs:href")}")
        }
        return links
    }

    fun getAllMedia(link: String): Elements? {
        val documentSelector = "[src]"
        var media: Elements? = getElements(link, documentSelector)
        media?.forEach {
            if (it.tagName() == "img") {
                println("Media ${it.attr("alt")} Links ${it.attr("abs:src")}")
            }
        }
        return media
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
