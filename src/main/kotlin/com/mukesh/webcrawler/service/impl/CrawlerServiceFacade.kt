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


    fun crawlSite(site: String, depth: Int): CrawlInformation {
        var depth = depth
        val blockingQueue = LinkedBlockingQueue<String>()
        val links = getAllLinks(site)
        val media = getAllMedia(site)

        val allLinks = mutableListOf<Link>()
        val allMedia = mutableListOf<Media>()
        allLinks.add(links)
        allMedia.add(media)

        blockingQueue.addAll(links.link.map { it }.toMutableList())

        val visitedLinks = mutableSetOf<String>()
        visitedLinks.add(site)
        while (depth > 1) {
            var newLinksToCrawl = mutableListOf<String>()
            while (blockingQueue.isNotEmpty()) {
                var crawlLink = site + blockingQueue.poll().substringAfter(site).split("/")[0]
                if (!visitedLinks.contains(crawlLink)) {
                    crawUnvisitedLinks(crawlLink, site, visitedLinks, allLinks, allMedia, newLinksToCrawl)
                }
            }
            depth--
            blockingQueue.addAll(newLinksToCrawl)
        }
        return CrawlInformation(allLinks, allMedia)
    }

    private fun crawUnvisitedLinks(crawlLink: String, site: String, visitedLinks: MutableSet<String>,
                                   result: MutableList<Link>, allMedia: MutableList<Media>, newLinksToCrawl: MutableList<String>) {
        if (crawlLink.contains(site) && crawlLink.length < 100) {
            visitedLinks.add(crawlLink)
            runBlocking {
                async {
                    val innerPages = getAllLinks(crawlLink)
                    result.add(innerPages)
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