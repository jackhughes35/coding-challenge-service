package com.coolplanet.challenge.exception;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ResponseExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(TaskServiceException.class)
	public final ResponseEntity<ErrorResponse> handleResourceNotFound(TaskServiceException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder().message(ex.getMessage()).timeStamp(LocalDateTime.now().toString()).build();
		if (ex instanceof ResourceNotFoundException) {
			log.error("Resource Not Found Exception :: {}", errorResponse.toString());
		} else if (ex instanceof TasksDataBaseException) {
			log.error("Database Exception thrown :: {}", errorResponse.toString());
		}
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder str = new StringBuilder();
		ex.getBindingResult().getAllErrors().stream()
		.forEach(error -> {
			str.append(error.getDefaultMessage());
			log.info("Validation Error :: {}", error.getDefaultMessage());
		});

		ErrorResponse errorResponse = ErrorResponse.builder().message(str.toString()).timeStamp(LocalDateTime.now().toString()).build();
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public final ResponseEntity<Object> catchConstraintException(DataIntegrityViolationException ex) {
	ErrorResponse errorResponse = ErrorResponse.builder().message(ex.getMessage()).timeStamp(LocalDateTime.now().toString()).build();
		log.error("Database Exception {} thrown :: {}", ex.getMostSpecificCause(), errorResponse.toString());
	
	return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleInternalError(Exception ex) {
		ErrorResponse errorResponse = ErrorResponse.builder().message(ex.getMessage()).timeStamp(LocalDateTime.now().toString()).build();
		log.error("Exception {} caught :: {}", ex.getClass(), errorResponse.toString());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
