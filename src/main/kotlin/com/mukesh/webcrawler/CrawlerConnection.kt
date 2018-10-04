package com.mukesh.webcrawler

import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CrawlerConnection{

    companion object {

        private val logger: Logger = LoggerFactory.getLogger(CrawlerConnection::class.java)

        fun getElements(link: String, documentSelector: String): Elements? {
            var elements: Elements? = null
            try {
                val connection = Jsoup.connect(link)
                val document = connection.get()
                if (connection.response().statusCode() == 200) {
                    elements = document.select(documentSelector)
                }
            } catch (ex: Exception) {
                logger.error("Something went wrong with $link")
                throw CrawlerException("Unable to crawl $link")
            }
            return elements
        }
    }
}
