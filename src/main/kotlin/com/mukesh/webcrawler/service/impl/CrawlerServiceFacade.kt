package com.mukesh.webcrawler.service.impl

import com.mukesh.webcrawler.data.CrawlInformation
import com.mukesh.webcrawler.data.Link
import com.mukesh.webcrawler.data.Media
import com.mukesh.webcrawler.service.LinkCrawler
import com.mukesh.webcrawler.service.MediaCrawler
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.LinkedBlockingQueue

@Service
class CrawlerServiceFacade : LinkCrawler, MediaCrawler {

    @Autowired
    @Qualifier("basicLink")
    lateinit var linkCrawler: LinkCrawler

    @Autowired
    @Qualifier("basicMedia")
    lateinit var mediaCrawler: MediaCrawler

    override fun getAllLinks(site: String): Link {
        return linkCrawler.getAllLinks(site) //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllMedia(site: String): Media {
        return mediaCrawler.getAllMedia(site)
    }


    val allLinks = mutableListOf<Link>()
    val allMedia = mutableListOf<Media>()

    fun crawlSite(site: String, depth: Int): CrawlInformation {

        var depth = depth

        val links = getAllLinks(site)
        val media = getAllMedia(site)
        allLinks.add(links)
        allMedia.add(media)

        val blockingQueue = LinkedBlockingQueue<String>()
        blockingQueue.addAll(links.link.map { it }.toMutableList())
        val visitedLinks = mutableSetOf<String>()
        visitedLinks.add(site)

        var depthCounter = 1
        while (depth > 1) {
            val newLinksToCrawl = mutableListOf<String>()
            while (blockingQueue.isNotEmpty()) {
                val crawlLink = getNextLink(site, blockingQueue.poll(), depthCounter)
                if (!visitedLinks.contains(crawlLink)) {
                    crawUnvisitedLinks(crawlLink, site, visitedLinks, newLinksToCrawl)
                }
            }
            depth--
            depthCounter++
            blockingQueue.addAll(newLinksToCrawl)
        }
        return CrawlInformation(allLinks, allMedia)
    }

    private fun getNextLink(site: String, nextLink: String, depthCounter: Int): String {
        val tokens = nextLink.substringAfter(site).split("/")
        var i = 1
        var newLink = ""
        while (tokens.size > i && i <= depthCounter) {
            newLink = "$newLink/${tokens[i++]}"
        }
        newLink = if (newLink.isBlank()) {
            nextLink
        } else {
            site + newLink
        }
        return newLink
    }

    private fun crawUnvisitedLinks(crawlLink: String, site: String, visitedLinks: MutableSet<String>,
                                   newLinksToCrawl: MutableList<String>) {
        if (crawlLink.startsWith(site) && crawlLink.length < 100) {
            visitedLinks.add(crawlLink)
            runBlocking {
                async {
                    val innerPages = getAllLinks(crawlLink)
                    allLinks.add(innerPages)
                    newLinksToCrawl.addAll(innerPages.link
                            .toList().subList(0, 50))
                }
                async {
                    val innerMedia = getAllMedia(crawlLink)
                    allMedia.add(innerMedia)
                }
            }
        }
    }
}