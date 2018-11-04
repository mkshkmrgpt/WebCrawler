package com.mukesh.webcrawler.data

data class Link(val name:String, val link: MutableList<String>)
data class Media(val name:String, val link:MutableList<String>)
data class CrawlInformation(val allLinks: MutableList<Link>, val allMedia: MutableList<Media>)

data class ApiError(var message: String?)

data class Page(var title:String, var links:MutableList<String> = mutableListOf(), var innerPage: Page?=null)