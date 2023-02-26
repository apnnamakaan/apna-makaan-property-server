package com.nvc.property.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nvc.property.responses.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handelNotFoundException(ResourceNotFoundException ex){
		ApiResponse exceptionResponse = new ApiResponse("false",ex.getMessage());
		return new ResponseEntity<ApiResponse>(exceptionResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UnauthorizeException.class)
	public ResponseEntity<ApiResponse> handelUnauthorizeException(UnauthorizeException ex){
		ApiResponse exceptionResponse = new ApiResponse("false",ex.getMessage());
		return new ResponseEntity<ApiResponse>(exceptionResponse,HttpStatus.UNAUTHORIZED);
	}
}

