package com.mukesh.webcrawler.service

import com.mukesh.webcrawler.data.Link
import com.mukesh.webcrawler.service.impl.BasicLinkCrawler
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test

class BasicLinkCrawlerTest{

    @Test
    fun whenCalledShouldReturnAllLinks(){
        val link = BasicLinkCrawler().getAllLinks("https://google.com/")
        assertTrue(link.link.size>0)
        assertEquals(link.name, "Google")
    }
}