package gg.wellplayed.backend.dataTransfer.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Una cas
 */
public class ApiResponse extends ResponseEntity<Object> {
	public ApiResponse(String message) {
		super(new Message(message), HttpStatus.OK);
	}

	public ApiResponse(String message, Object data) {
		super(new ObjectWithMessage(message, data), HttpStatus.OK);
	}

	public ApiResponse(String message, HttpStatus status) {
		super(new Message(message), status);
	}

	public ApiResponse(String message, Object data, HttpStatus status) {
		super(new ObjectWithMessage(message, data), status);
	}
}
