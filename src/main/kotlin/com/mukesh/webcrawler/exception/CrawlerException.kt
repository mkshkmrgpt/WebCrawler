package com.mukesh.webcrawler.exception

import com.mukesh.webcrawler.data.ApiError
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

class CrawlerException(override val message: String?) : Exception(message)

@ControllerAdvice
class CrawlerExceptionHandler {

    val logger = LoggerFactory.getLogger(CrawlerExceptionHandler::class.java)

    @ExceptionHandler(CrawlerException::class)
    fun handleCrawlerException(crawlerException: CrawlerException, request: WebRequest): ResponseEntity<ApiError> {
        return ResponseEntity(ApiError(crawlerException.message), HttpHeaders(), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleAllOtherException(exception: Exception, request: WebRequest): ResponseEntity<ApiError> {
        logger.error("Error occured ", exception)
        return ResponseEntity(ApiError(exception.message), HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}