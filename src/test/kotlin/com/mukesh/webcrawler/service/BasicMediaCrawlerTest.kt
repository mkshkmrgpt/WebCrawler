package com.mukesh.webcrawler.service

import com.mukesh.webcrawler.service.impl.BasicMediaCrawler
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test

class BasicMediaCrawlerTest {

    @Test
    fun whenCalledShouldReturnAllMediaLinksOnPage(){
        val media = BasicMediaCrawler().getAllMedia("http://google.com")
        assertTrue(media.link.size>0)
        assertEquals("Google", media.name)
    }
}