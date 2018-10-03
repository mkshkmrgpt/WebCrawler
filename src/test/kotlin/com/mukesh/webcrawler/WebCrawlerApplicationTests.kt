package com.mukesh.webcrawler

import junit.framework.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebCrawlerApplicationTests {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun whenCalledShouldReturnJsonData() {
        val result = restTemplate.getForEntity("/crawl?site=https://www.google.com",
                CrawlInformation::class.java)
        assertNotNull(result)
        assertEquals(HttpStatus.OK, result.statusCode)
    }

    @Test
    fun whenInvalidURLIsProvidedShouldReturnErrorMessage() {
        val result = restTemplate.getForEntity("/crawl?site=hps://www.google.com",
                CrawlInformation::class.java)
        assertNotNull(result)
        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
    }
}