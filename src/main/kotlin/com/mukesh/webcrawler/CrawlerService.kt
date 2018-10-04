package com.mukesh.webcrawler

import org.springframework.stereotype.Service

@Service
class BasicCrawlerService : LinkCrawler by BasicLinkCrawler, MediaCrawler by BasicMediaCrawler
