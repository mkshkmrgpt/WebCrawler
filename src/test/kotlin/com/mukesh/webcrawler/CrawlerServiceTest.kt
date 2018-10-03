package com.mukesh.webcrawler

import junit.framework.Assert.assertTrue
import org.jsoup.Jsoup
import org.junit.Test

class CrawlerServiceTest {

    val link = "https://www.prudential.co.uk/"

    @Test
    fun whenCalledShouldReturnAllLinks() {
        val connection = Jsoup.connect(link)
        val document = connection.get()
        if (connection.response().statusCode() == 200) {
            val links = document.select("a[href]")
            links.forEach {
                println("Page ${it.text()} Links ${it.attr("abs:href")}")
            }
            assertTrue(links.isNotEmpty())
        }
    }

    @Test
    fun whenCalledShouldReturnAllMediaFiles() {
        val connection = Jsoup.connect(link)
        val document = connection.get()
        if (connection.response().statusCode() == 200) {
            val media = document.select("[src]")
            media.forEach {
                if (it.tagName() == "img") {
                    println("Media ${it.text()} Links ${it.attr("abs:src")}")
                }
            }
            assertTrue(media.isNotEmpty())
        }
    }

    @Test
    fun whenCalledShuldReturnAllSrces() {

    }
}