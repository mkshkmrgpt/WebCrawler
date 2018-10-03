package com.mukesh.webcrawler

import junit.framework.Assert.assertNotNull
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ControllerTest{

    @InjectMocks
    lateinit var crawlerController: CrawlerController

    @Mock
    lateinit var crawlerService: BasicCrawlerService

    @Test
    @Ignore
    fun whenCalledShouldReturnJsonData(){

        doReturn(mutableListOf<Media>()).`when`(crawlerService.getAllMedia(ArgumentMatchers.anyString()))
        doReturn(mutableListOf<Link>()).`when`(crawlerService.getAllLinks(ArgumentMatchers.anyString()))
        val data = crawlerController.crawl(ArgumentMatchers.anyString())
        assertNotNull(data)
    }
}