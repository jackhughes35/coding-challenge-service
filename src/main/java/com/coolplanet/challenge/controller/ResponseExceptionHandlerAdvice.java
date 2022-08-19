package com.coolplanet.challenge.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coolplanet.challenge.exception.ErrorResponse;
import com.coolplanet.challenge.exception.ResourceNotFoundException;


@RestControllerAdvice
public class ResponseExceptionHandlerAdvice extends ResponseEntityExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex){
		ErrorResponse errorResponse = ErrorResponse.builder().message(ex.getMessage()).timeStamp(LocalDateTime.now().toString()).build();
		logger.error("Resource Not Found Exception :: {}", errorResponse.toString());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);		
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleInternalError(Exception ex){
		ErrorResponse errorResponse = ErrorResponse.builder().message(ex.getMessage()).timeStamp(LocalDateTime.now().toString()).build();
		logger.error("Exception {} caught :: {}", ex.getClass(), errorResponse.toString());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);		
	}
}
