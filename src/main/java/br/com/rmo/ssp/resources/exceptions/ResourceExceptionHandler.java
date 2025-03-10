package br.com.rmo.ssp.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.rmo.ssp.services.exceptions.DatabaseException;
import br.com.rmo.ssp.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	// CONTROLANDO A EXCEÇÃO ENTITY NOT FOUND:

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError error = new StandardError();

		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError(e.getMessage());
		error.setMessage("SSP ERROR: Entity not found.");
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(error);

	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError();

		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError(e.getMessage());
		error.setMessage("SSP ERROR: Database Exception.");
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(error);

	}
}
