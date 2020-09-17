package ru.shop.forum.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.shop.forum.entities.AbstractEntity;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(value = {EntityNotFoundException.class})
	public ResponseEntity<String> entityNotFound(RuntimeException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nothing found for id = " + ex.getMessage());
	}
}
