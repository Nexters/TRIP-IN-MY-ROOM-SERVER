package com.trip.my.room.server.common.exception

import com.trip.my.room.server.user.exception.IfErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.util.*

@RestControllerAdvice
class NotFoundExceptionAdvice {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(
        ex: NoSuchElementException,
        request: WebRequest
    ): ResponseEntity<*> {
        return ResponseEntity(
            IfErrorResponse("400", "There is not any elements related to your input. message=${ex.message}"),
            HttpStatus.BAD_REQUEST
        )
    }

}
