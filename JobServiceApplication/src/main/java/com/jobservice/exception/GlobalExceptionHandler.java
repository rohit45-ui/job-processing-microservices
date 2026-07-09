package com.jobservice.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jobservice.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(JobNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleJobNotFoundException(JobNotFoundException ex,
																		HttpServletRequest request){
		ErrorResponse response = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.name(),
				ex.getMessage(),
				request.getRequestURI());
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationException(
	        MethodArgumentNotValidException ex) {

	    Map<String, String> errors = new HashMap<>();

	    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error ->
                errors.putIfAbsent(error.getField(), error.getDefaultMessage()));

	    Map<String, Object> response = new HashMap<>();

	    response.put("timeStamp", LocalDateTime.now());
	    response.put("status", 400);
	    response.put("error", "Validation Failed");
	    response.put("errors", errors);

	    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex) {

		ErrorResponse response = new ErrorResponse();

		response.setTimeStamp(LocalDateTime.now());
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setError("Job Not Found");
		response.setMessage(ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
