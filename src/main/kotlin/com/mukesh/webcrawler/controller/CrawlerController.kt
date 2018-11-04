package com.mukesh.webcrawler.controller

import com.mukesh.webcrawler.service.impl.CrawlerServiceFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CrawlerController {

    @Value("\${crawler.depth}")
    var depth: Int = 0

    @Autowired
    lateinit var crawlerServiceFacade: CrawlerServiceFacade

    @GetMapping("/crawl", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun crawl(@RequestParam("site") site: String, @RequestParam(value = "depth", required = false) depth:Integer?): Any? {
        if(depth!=null){
            this.depth = depth.toInt()
        }
        return crawlerServiceFacade.crawlSite(site, this.depth)
    }
}

