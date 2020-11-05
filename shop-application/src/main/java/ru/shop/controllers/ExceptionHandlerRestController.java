package ru.shop.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class ExceptionHandlerRestController {
	
	@ExceptionHandler(value = {EntityNotFoundException.class})
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ResponseObject entityNotFound(RuntimeException ex, WebRequest request) {
		return new ResponseObject("Nothing found for id = " + Objects.requireNonNullElse(ex.getMessage(), "null"));
	}
	
	@ExceptionHandler(value = {IllegalArgumentException.class})
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseObject illegalArgsException(RuntimeException ex, WebRequest request) {
		return new ResponseObject(ex.getMessage());
	}
	
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
	public ResponseObject entityNotValid(MethodArgumentNotValidException ex) {
		List<FieldValidationError> validationErrors =
				ex.getBindingResult().getFieldErrors().stream().map(FieldValidationError::new).collect(Collectors.toList());
		return new ResponseObject("A not acceptable entity has been received with not valid fields.", validationErrors);
	}
	
	@ExceptionHandler(value = NullPointerException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseObject nullPointerException(NullPointerException e, WebRequest request) {
		return new ResponseObject(e.getMessage());
	}
	
	@ExceptionHandler(value = AccessDeniedException.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ResponseObject accessDenied(RuntimeException e, WebRequest request) {
		return new ResponseObject(e.getMessage() == null ? "NULL" : e.getMessage());
	}
	
	@ExceptionHandler(value = RuntimeException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseObject runtimeException(RuntimeException e, WebRequest request) {
		return new ResponseObject(e.getMessage() == null ? "NULL" : e.getMessage());
	}
	
	
	@Data
	@NoArgsConstructor
	public static class ResponseObject implements Serializable {
		
		@JsonInclude(content = JsonInclude.Include.NON_EMPTY)
		private String errorMessage;
		
		@JsonInclude(content = JsonInclude.Include.NON_EMPTY)
		private String localizedMessage;
		
		@JsonInclude(content = JsonInclude.Include.NON_NULL)
		private List<FieldValidationError> fieldValidationErrors;
		
		public ResponseObject(@Nullable String errorMessage) {
			this.errorMessage = errorMessage != null ? errorMessage : "";
		}
		
		public ResponseObject(@Nullable String errorMessage, List<FieldValidationError> fieldValidationErrors) {
			this.errorMessage = errorMessage != null ? errorMessage : "";
			this.fieldValidationErrors = fieldValidationErrors;
		}
		
		public ResponseObject(RuntimeException exception) {
			if (exception.getMessage() != null) {
				this.errorMessage = exception.getMessage();
			} else if (exception.getCause() != null && exception.getCause().getMessage() != null) {
				this.errorMessage = exception.getCause().getMessage();
			} else {
				this.errorMessage = "";
			}
			this.localizedMessage = exception.getLocalizedMessage() != null ? exception.getLocalizedMessage() : "";
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class FieldValidationError implements Serializable {
		
		@JsonInclude(content = JsonInclude.Include.NON_EMPTY)
		private String fieldName;
		
		@JsonInclude(content = JsonInclude.Include.NON_EMPTY)
		private String errorMessage;
		
		@JsonInclude(content = JsonInclude.Include.NON_EMPTY)
		private Object rejectedValue;
		
		public FieldValidationError(FieldError fieldError) {
			this.fieldName = fieldError.getField();
			this.errorMessage = Objects.requireNonNullElse(fieldError.getDefaultMessage(), "No special message left.");
			this.rejectedValue = Objects.requireNonNullElse(fieldError.getRejectedValue(), "null");
		}
	}
}
