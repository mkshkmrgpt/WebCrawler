package com.mukesh.webcrawler.controller

import com.mukesh.webcrawler.data.CrawlInformation
import com.mukesh.webcrawler.service.impl.CrawlerServiceFacade
import com.nhaarman.mockito_kotlin.any
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
    lateinit var crawlerServiceFacade: CrawlerServiceFacade

    @Test
    fun whenCalledShouldReturnJsonData(){
        val crawlInformation = CrawlInformation(mutableListOf(), mutableListOf())
        whenever(crawlerServiceFacade.crawlSite(ArgumentMatchers.anyString(), any())).thenReturn(crawlInformation)
        val data = crawlerController.crawl(ArgumentMatchers.anyString(), any())
        assertNotNull(data)
    }
}