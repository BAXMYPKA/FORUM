package ru.shop.forum.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.shop.forum.entities.AbstractEntity;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ExceptionHandlerRestController {
	
	@ExceptionHandler(value = {EntityNotFoundException.class})
	public ResponseEntity<String> entityNotFound(RuntimeException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nothing found for id = " + ex.getMessage());
	}
	
	@ExceptionHandler(value = {IllegalArgumentException.class})
	public ResponseEntity<String> illegalArgsException(RuntimeException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
	
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ResponseEntity<String> entityNotValid(MethodArgumentNotValidException ex, WebRequest webRequest) {
		
		//TODO: to make a response as a JSON with a detailed description
		
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ex.getMessage());
	}
	
	@ExceptionHandler(value = NullPointerException.class)
	public ResponseEntity<String> nullPointerException(NullPointerException e, WebRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
}
