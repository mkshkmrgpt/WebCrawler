package com.mukesh.webcrawler

data class Link(val name:String , val link: String)
data class Media(val name:String, val link:String)
data class CrawlInformation(val allLinks: MutableList<Link> = mutableListOf(),
                            val allMedia: MutableList<Media> = mutableListOf())

data class ApiError(var message: String?)