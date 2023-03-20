package com.speaklearn.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.TypeMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.speaklearn.exception.message.ApiResponseError;

@ControllerAdvice
public class SpeakLearnExceptionHandler extends ResponseEntityExceptionHandler {
	
	//better to log every exception
	Logger logger = LoggerFactory.getLogger(SpeakLearnExceptionHandler.class);
	
	private ResponseEntity<Object> buildResponseEntity(ApiResponseError error){
		logger.error(error.getMessage());
		return new ResponseEntity<>(error, error.getStatus());
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request){
		
		ApiResponseError error = new ApiResponseError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getDescription(false));
		return buildResponseEntity(error);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		//since there might be multiple fields invalid, getting them all.
		List<String> errorFields = ex.getBindingResult().getFieldErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
		
		ApiResponseError error = new ApiResponseError(HttpStatus.BAD_REQUEST, errorFields.get(0).toString(), request.getDescription(false));
		return buildResponseEntity(error);
	}
	
	@ExceptionHandler(TypeMismatchException.class)
	protected ResponseEntity<Object> handleTypeMisMatchException(TypeMismatchException ex, WebRequest request){
		ApiResponseError error = new ApiResponseError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false));
		return buildResponseEntity(error);
	}
	
	
	
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request){
		ApiResponseError error = new ApiResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getDescription(false));
		return buildResponseEntity(error);
	}
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request){
		
//old school
//		Map<String, String> map = new HashedMap<>();
//		map.put("time", LocalDateTime.now().toString());
//		map.put("message", ex.getMessage());		
//		map.put("path", request.getDescription(false));
//		
//		return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		
		ApiResponseError error = new ApiResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getDescription(false));
		return buildResponseEntity(error);
	}
}
