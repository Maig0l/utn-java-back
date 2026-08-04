package gg.wellplayed.backend.config;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import gg.wellplayed.backend.dataTransfer.api.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Red de seguridad para los controllers que resuelven un id (propio o de una
	// relación referenciada en el body) sin envolverlo en su propio try/catch —
	// sin esto, un id inexistente revienta con 500 y sin mensaje en vez de un 404.
	@ExceptionHandler(NoSuchElementException.class)
	public ApiResponse handleNotFound(NoSuchElementException e) {
		return new ApiResponse("Recurso no encontrado", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ApiResponse handleForbidden(AccessDeniedException e) {
		return new ApiResponse("Forbidden", HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResponse handleValidation(MethodArgumentNotValidException e) {
		String message = e.getBindingResult().getFieldErrors().stream()
			.map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
			.collect(Collectors.joining(", "));
		return new ApiResponse(message, HttpStatus.BAD_REQUEST);
	}
}
