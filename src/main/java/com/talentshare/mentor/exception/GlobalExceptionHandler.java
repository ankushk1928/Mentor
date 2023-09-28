package com.talentshare.mentor.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.talentshare.mentor.constants.MessageConstants;
import com.talentshare.mentor.dto.ErrorDetails;
import com.talentshare.mentor.dto.ValidationError;
import com.talentshare.mentor.utils.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@Autowired
	private CommonUtil commonUtil;
    
    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException exception, HttpServletRequest request) {
    	
    	log.error("EmailAlreadyRegisteredException: ", MessageConstants.EMAIL_REGISTERED);
    	
    	ErrorDetails setResponse = commonUtil.errorResponse(HttpStatus.CONFLICT.value(), exception.getLocalizedMessage(),  exception.getMessage(), request.getRequestURI());
    	return new ResponseEntity<>(setResponse,HttpStatus.CONFLICT);

    }
    
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception,HttpServletRequest request) {
    	log.error("ResourceNotFoundException: ", MessageConstants.RESOURCE_NOT_FOUND);
    	
    	ErrorDetails setResponse = commonUtil.errorResponse(HttpStatus.NOT_FOUND.value(), exception.getLocalizedMessage(),  exception.getMessage(), request.getRequestURI());
    	return new ResponseEntity<>(setResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(WrongCredentialsException.class)
	 public ResponseEntity<Object> handleWrongCredentialsException(WrongCredentialsException ex, HttpServletRequest httpServletRequest){
		 
    	ErrorDetails errorResponse = new ErrorDetails(400, "Invalid Credetials", ex.getMessage(),LocalDateTime.now(), httpServletRequest.getRequestURI());
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	 }
	 
    
    @ExceptionHandler(EmployeeIdAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleEmployeeIdAlreadyRegisteredException(EmployeeIdAlreadyRegisteredException exception, HttpServletRequest request) {
    	
    	log.error("EmployeeIdAlreadyRegisteredException: ", MessageConstants.EMPLOYEEID_REGISTERED);
    	
    	ErrorDetails setResponse = commonUtil.errorResponse(HttpStatus.CONFLICT.value(), exception.getLocalizedMessage(),  exception.getMessage(), request.getRequestURI());
    	return new ResponseEntity<>(setResponse,HttpStatus.CONFLICT);

    }
    
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex, HttpServletRequest request){
    
    	log.info("Method Argument Not Valid exception called ");
    
    	Map<String, String> errors = new HashMap<>();
    	ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
  
    	ValidationError errorResponse = new ValidationError(HttpStatus.BAD_REQUEST.value(), MessageConstants.VALIDATION_ERROR, errors ,LocalDateTime.now(), request.getRequestURI());
    	return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
