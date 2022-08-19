package com.coolplanet.challenge.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.coolplanet.challenge.exception.ErrorResponse;
import com.coolplanet.challenge.exception.ResourceNotFoundException;
import com.coolplanet.challenge.exception.TaskServiceException;
import com.coolplanet.challenge.exception.TasksDataBaseException;

import lombok.extern.slf4j.Slf4j;


@RestControllerAdvice
@Slf4j
public class ResponseExceptionHandlerAdvice extends ResponseEntityExceptionHandler {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(TaskServiceException.class)
	public final ResponseEntity<ErrorResponse> handleResourceNotFound(TaskServiceException ex){
		ErrorResponse errorResponse = ErrorResponse.builder().message(ex.getMessage()).timeStamp(LocalDateTime.now().toString()).build();
		if(ex instanceof ResourceNotFoundException) {
			log.error("Resource Not Found Exception :: {}", errorResponse.toString());
		} else if(ex instanceof TasksDataBaseException) {
			log.error("Database Exception thrown :: {}", errorResponse.toString());
		}
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);		
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleInternalError(Exception ex){
		ErrorResponse errorResponse = ErrorResponse.builder().message(ex.getMessage()).timeStamp(LocalDateTime.now().toString()).build();
		log.error("Exception {} caught :: {}", ex.getClass(), errorResponse.toString());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);		
	}
}
