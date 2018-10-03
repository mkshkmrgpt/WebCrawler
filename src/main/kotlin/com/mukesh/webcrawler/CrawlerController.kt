package com.mukesh.webcrawler

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CrawlerController {

    @Autowired
    lateinit var basicCrawlerService: BasicCrawlerService

    @GetMapping("/crawl")
    fun crawl(@RequestParam("site") site: String): Any? {
        var crawlInformation = CrawlInformation()
        runBlocking {
            val links = async { basicCrawlerService.getAllLinks(site) }
            val media = async { basicCrawlerService.getAllMedia(site) }

            crawlInformation = CrawlInformation(links.await(), media.await())
        }
        return crawlInformation
    }
}

