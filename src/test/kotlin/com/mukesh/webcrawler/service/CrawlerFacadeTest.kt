package com.mukesh.webcrawler.service

import com.mukesh.webcrawler.service.impl.CrawlerServiceFacade
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test

class CrawlerFacadeTest {

    @Test
    fun whenCalledShouldReturnAllLinksAndMediaOnHomePage(){
        var crawlInfo = CrawlerServiceFacade().crawlSite("http://google.com", 1)
        assertEquals(1,crawlInfo.allLinks.size)
        assertEquals(1, crawlInfo.allMedia.size)
    }

    @Test
    fun shouldVisitChildLinksIfDepthIsGreaterThan2(){
        var crawlInfo = CrawlerServiceFacade().crawlSite("https://www.google.com", 2)
        assertTrue(crawlInfo.allLinks.size>1)
        assertTrue(crawlInfo.allMedia.size>1)
    }
}