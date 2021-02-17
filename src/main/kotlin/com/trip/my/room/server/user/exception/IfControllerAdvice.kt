package com.trip.my.room.server.user.exception

import org.apache.commons.lang3.exception.ExceptionUtils
import org.hibernate.exception.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class IfControllerAdvice {
	
	@ExceptionHandler(IfException::class)
	@ResponseBody
	fun handleIfException(
			request: HttpServletRequest,
			ex: IfException
	
	): ResponseEntity<*> {
		
		return ResponseEntity(
				IfErrorResponse(
						ex.errorCode.name,
						ex.errorMessage
				),
				ex.httpStatus
		)
	}
	
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException::class)
	@ResponseBody
	fun handleHttpRequestMethodNotSupportedException(
			
			request: HttpServletRequest,
			ex: HttpRequestMethodNotSupportedException
	
	): ResponseEntity<*> {
		
		return this.handleIfException(
				request,
				IfException(
						IfErrorCode.UNSUPPORTED_HTTP_METHOD,
						IfErrorCode.UNSUPPORTED_HTTP_METHOD.message,
						HttpStatus.BAD_REQUEST
				)
		)
	}
	
	
	@ExceptionHandler(value = [HttpMessageConversionException::class, HttpMessageNotReadableException::class])
	@ResponseBody
	fun handleInvalidHttpMessageBodyException(
			
			request: HttpServletRequest,
			ex: Exception
	
	): ResponseEntity<*> {
		
		val rootException = ExceptionUtils.getRootCause(ex)
		if (rootException is IfException) {
			return handleIfException(request, rootException)
		}
		
		return this.handleIfException(
				request,
				IfException(
						IfErrorCode.INVALID_HTTP_MESSAGE_BODY,
						IfErrorCode.INVALID_HTTP_MESSAGE_BODY.message,
						HttpStatus.BAD_REQUEST
				)
		)
	}
	
	@ExceptionHandler(value = [BindException::class, MethodArgumentNotValidException::class, ConstraintViolationException::class])
	@ResponseBody
	fun handleHttpRequestValidationException(
			
			request: HttpServletRequest,
			ex: Exception
	
	): ResponseEntity<*> {
		
		val errorCode = when (ex) {
			is BindException -> ex.bindingResult.allErrors[0].defaultMessage
			is MethodArgumentNotValidException -> ex.bindingResult.allErrors[0].defaultMessage
			is ConstraintViolationException -> ex.constraintName  // error 주의
			else -> null
		}
		val error = IfErrorCode.valueOf(errorCode ?: IfErrorCode.INTERNAL_SERVER_ERROR.name)
		
		return this.handleIfException(
				request,
				IfException(
						error,
						error.message,
						HttpStatus.BAD_REQUEST
				)
		)
	}
	
	@ExceptionHandler(Exception::class)
	@ResponseBody
	fun handleException(
			request: HttpServletRequest,
			ex: Exception
	
	): ResponseEntity<*> {
		
		return ResponseEntity(
				IfErrorResponse
				(
						IfErrorCode.INTERNAL_SERVER_ERROR.name,
						IfErrorCode.INTERNAL_SERVER_ERROR.message
				),
				HttpStatus.INTERNAL_SERVER_ERROR
		)
	}
	
	
}