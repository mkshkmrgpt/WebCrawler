package com.mukesh.webcrawler

import junit.framework.Assert.assertTrue
import org.jsoup.Jsoup
import org.junit.Test

class CrawlerServiceTest {

    private val link = "https://www.google.com/"

    @Test
    fun whenCalledShouldReturnAllLinks() {
        var links: List<Link>? = BasicCrawlerService().getAllLinks(link)
        assertTrue(links?.isNotEmpty()!!)

        val visitedLinks = mutableSetOf<String>()
        var innerLinks = links.map { it }.filter { it.link.contains(link) }.toList()
        var siteMap = SiteMap(link = link, childLinks = innerLinks.map { it.link }.toList())
        var result = siteMap
        innerLinks.forEach { it ->
            if(visitedLinks.contains(it.link)){

            }else{
                visitedLinks.add(it.link)
                var chilidLinks = BasicCrawlerService().getAllLinks(it.link)
                        .map { it.link }
                        .filter { it.contains(link) }
                        .toList()
                val next = SiteMap(link = it.link, childLinks = chilidLinks)
                siteMap.siteMap = next
                siteMap = next
            }
        }
        println(result)
    }

    data class SiteMap(var link:String, var childLinks:List<String>, var siteMap:SiteMap?=null)

    @Test
    fun whenCalledShouldReturnAllMediaFiles() {
        var media: List<Media> = BasicCrawlerService().getAllMedia(link)
        assertTrue(media?.isNotEmpty()!!)
    }

    @Test(expected = CrawlerException::class)
    fun whenSiteIsNotReachableShouldThrowException(){
        BasicCrawlerService().getAllLinks("")
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