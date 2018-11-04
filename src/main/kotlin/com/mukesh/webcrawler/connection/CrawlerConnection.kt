package com.mukesh.webcrawler.connection

import com.mukesh.webcrawler.exception.CrawlerException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CrawlerConnection {

    private val logger: Logger = LoggerFactory.getLogger(CrawlerConnection::class.java)

    fun getDocument(link: String): Any? {
        val document: Document
        try {
            if (link.isBlank()) {
                logger.info("No site info")
                throw CrawlerException("Invalid link")
            }
            val connection = Jsoup.connect(link)
            document = connection.get()
            if (connection.response().statusCode() == 200) {
                return document
            }
        } catch (ex: Exception) {
            //ignore
            logger.error("Site is not accessible  $link")
        }
        return Unit
    }
}
