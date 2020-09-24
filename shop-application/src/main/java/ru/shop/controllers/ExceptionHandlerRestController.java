package ru.shop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@RestController
public class ExceptionHandlerRestController {
	
	@ExceptionHandler(value = {EntityNotFoundException.class})
	public ResponseEntity<String> entityNotFound(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<>("Nothing found for id = " + ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {IllegalArgumentException.class})
	public ResponseEntity<String> illegalArgsException(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ResponseEntity<String> entityNotValid(MethodArgumentNotValidException ex) {

//		TODO: to make a response as a JSON with a detailed description

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(value = NullPointerException.class)
	public ResponseEntity<String> nullPointerException(NullPointerException e, WebRequest request) {
		System.out.println(e.getMessage());
		return new ResponseEntity<>("dsfdsf", HttpStatus.BAD_REQUEST);
	}
}
