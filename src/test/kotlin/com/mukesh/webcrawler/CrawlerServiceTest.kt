package com.mukesh.webcrawler

import junit.framework.Assert.assertTrue
import org.jsoup.Jsoup
import org.junit.Test

class CrawlerServiceTest {

    val link = "http://www.google.com"

    @Test
    fun whenCalledShouldReturnAllLinks(){
        var connection = Jsoup.connect(link)
        var document = connection.get()
        if(connection.response().statusCode() == 200){
            var links = document.select("a[href]")
            links.forEach {
                println("Page ${it.text()} Links ${it.attr("abs:href")}") }
            assertTrue(links.isNotEmpty())
        }
    }

    @Test
    fun whenCalledShouldReturnAllMediaFiles(){

    }

    @Test
    fun whenCalledShuldReturnAllSrces(){

    }
}