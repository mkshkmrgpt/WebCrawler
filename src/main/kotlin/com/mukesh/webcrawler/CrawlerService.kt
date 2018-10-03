package com.mukesh.webcrawler

import org.jsoup.Jsoup
import org.jsoup.select.Elements

class CrawlerService {

    fun getAllLinks(link: String): Elements? {
        val connection = Jsoup.connect(link)
        val document = connection.get()
        var links: Elements? = null
        if (connection.response().statusCode() == 200) {
            links = document.select("a[href]")
            links.forEach {
                println("Page ${it.text()} Links ${it.attr("abs:href")}")
            }
        }
        return links
    }


    fun getAllMedia(link: String): Elements? {
        val connection = Jsoup.connect(link)
        val document = connection.get()
        var media: Elements? = null
        if (connection.response().statusCode() == 200) {
            media = document.select("[src]")
            media.forEach {
                if (it.tagName() == "img") {
                    println("Media ${it.attr("alt")} Links ${it.attr("abs:src")}")
                }
            }
        }
        return media
    }
}
