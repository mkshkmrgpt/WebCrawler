package com.mukesh.webcrawler

interface LinkCrawler {
    fun getAllLinks(site:String): MutableList<Link>
}

interface MediaCrawler {
    fun getAllMedia(site:String): MutableList<Media>
}