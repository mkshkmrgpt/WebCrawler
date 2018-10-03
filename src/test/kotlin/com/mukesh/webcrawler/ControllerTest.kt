package com.mukesh.webcrawler

import com.nhaarman.mockito_kotlin.whenever
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ControllerTest{

    @InjectMocks
    lateinit var crawlerController: CrawlerController

    @Mock
    lateinit var crawlerService: BasicCrawlerService

    @Test
    fun whenCalledShouldReturnJsonData(){

        whenever(crawlerService.getAllMedia(ArgumentMatchers.anyString())).thenReturn(mutableListOf())
        whenever(crawlerService.getAllLinks(ArgumentMatchers.anyString())).thenReturn(mutableListOf())
        val data = crawlerController.crawl(ArgumentMatchers.anyString())
        assertNotNull(data)
    }
}