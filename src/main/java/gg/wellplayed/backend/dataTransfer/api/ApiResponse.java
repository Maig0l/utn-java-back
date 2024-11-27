package gg.wellplayed.backend.dataTransfer.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Un wrapper de {@link ResponseEntity} para devolver respuestas desde la API
 * con un formato particular en el body.
 * 
 * <p>
 * Provee shorthands para enviar sólo un mensaje, un objeto con un mensaje
 * adicional, o cualquiera de estas con un status code particular (por defecto
 * devuelve 200 OK).
 * </p>
 */
public class ApiResponse extends ResponseEntity<Object> {
	public ApiResponse(String message) {
		super(new Message(message), HttpStatus.OK);
	}

	public ApiResponse(String message, Object data) {
		super(new ObjectWithMessage(message, data), HttpStatus.OK);
	}
	
	public ApiResponse( Object data) {
		super(new ObjectOnly(data), HttpStatus.OK);
	}


	public ApiResponse(String message, HttpStatus status) {
		super(new Message(message), status);
	}
	public ApiResponse( HttpStatus status) {
		super (status);
	}

	public ApiResponse(String message, Object data, HttpStatus status) {
		super(new ObjectWithMessage(message, data), status);
	}
}