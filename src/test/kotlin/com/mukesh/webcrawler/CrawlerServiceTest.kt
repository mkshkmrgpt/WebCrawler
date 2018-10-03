package com.mukesh.webcrawler

import junit.framework.Assert.assertTrue
import org.jsoup.Jsoup
import org.junit.Test

class CrawlerServiceTest {

    private val link = "https://www.prudential.co.uk/"

    @Test
    fun whenCalledShouldReturnAllLinks() {
        var links: List<Link>? = CrawlerService().getAllLinks(link)
        assertTrue(links?.isNotEmpty()!!)
    }

    @Test
    fun whenCalledShouldReturnAllMediaFiles() {
        var media: List<Media> = CrawlerService().getAllMedia(link)
        assertTrue(media?.isNotEmpty()!!)
    }

    @Test
    fun whenCalledShuldReturnAllImports() {
        val connection = Jsoup.connect(link)
        val document = connection.get()
        if (connection.response().statusCode() == 200) {
            val imports = document.select("link[href]")
            imports.forEach {
                println("${it.attr("rel")} Link ${it.attr("abs:href")}")
            }
            assertTrue(imports.isNotEmpty())
        }
    }
}