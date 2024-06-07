package com.nttdata.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Clase que maneja globalmente las excepciones de la aplicación y proporciona
 * respuestas HTTP adecuadas.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Maneja la excepción EntityNotFoundException y devuelve una respuesta con
	 * estado 404 (NOT_FOUND).
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Maneja la excepción UserNotFoundException y devuelve una respuesta con estado
	 * 404 (NOT_FOUND).
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Maneja la excepción BookNotFoundException y devuelve una respuesta con estado
	 * 404 (NOT_FOUND).
	 */
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Maneja la excepción UserBookNotFoundException y devuelve una respuesta con
	 * estado 404 (NOT_FOUND).
	 */
	@ExceptionHandler(UserBookNotFoundException.class)
	public ResponseEntity<String> handleUserBookNotFoundException(UserBookNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Maneja la excepción InvalidRequestException y devuelve una respuesta con
	 * estado 400 (BAD_REQUEST).
	 */
	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<String> handleInvalidRequestException(InvalidRequestException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Maneja la excepción DatabaseException y devuelve una respuesta con estado 500
	 * (INTERNAL_SERVER_ERROR).
	 */
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<String> handleDatabaseException(DatabaseException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Maneja cualquier otra excepción no especificada y devuelve una respuesta con
	 * estado 500 (INTERNAL_SERVER_ERROR).
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
		return new ResponseEntity<>("Ocurrió un error interno", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
