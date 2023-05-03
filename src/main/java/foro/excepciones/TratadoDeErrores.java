package foro.excepciones;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import foro.dto.DatosErrorValidacion;

@RestControllerAdvice
public class TratadoDeErrores {

	// Maneja las peticiones inválidas
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity tratarError400(MethodArgumentNotValidException exception) {
		var errores = exception.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
		return ResponseEntity.badRequest().body(errores);
	}

}