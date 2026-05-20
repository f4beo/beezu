package com.beezu.beezu_api.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ UserNotFoundException.class, DisciplineNotFoundException.class,
			ActivityNotExistsException.class, EnrollmentDoesNotExistException.class, ActivityNotFoundException.class})
	public ResponseEntity<StandardError> notFound(RuntimeException ex, HttpServletRequest request) {
		return buildError(HttpStatus.NOT_FOUND, "Resource not found", ex, request);
	}

	@ExceptionHandler({ EmailAlreadyRegisteredException.class, EnrollmentAlreadyExistsException.class, UserAlreadyEnrolledException.class})
	public ResponseEntity<StandardError> conflict(RuntimeException ex, HttpServletRequest request) {
		return buildError(HttpStatus.CONFLICT, "Business rule violation", ex, request);
	}
	
	@ExceptionHandler({ InvalidActivityDeadlineException.class})
	public ResponseEntity<StandardError> badRequest(RuntimeException ex, HttpServletRequest request) {
		return buildError(HttpStatus.BAD_REQUEST, "Invalid request", ex, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<StandardError> generic(Exception ex, HttpServletRequest request) {
		return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex, request);
	}
	

	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException ex, HttpServletRequest request) {
	    String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
	    return buildError(HttpStatus.BAD_REQUEST, "Validation error", new Exception(message), request);
	}

	private ResponseEntity<StandardError> buildError(HttpStatus status, String error, Exception ex,
			HttpServletRequest request) {

		StandardError err = new StandardError(Instant.now(), status.value(), error, ex.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
}
