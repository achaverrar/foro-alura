package foro.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import foro.dto.excepciones.DatosErrorApi;
import foro.dto.excepciones.DatosErrorValidacion;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadoDeErrores {

	// Maneja las peticiones con información incompleta/nula
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> tratarError400(MethodArgumentNotValidException excepcion) {
		var errores = excepcion.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
		return ResponseEntity.badRequest().body(errores);
	}

	// Maneja las peticiones cuyo cuerpo en JSON no se puede leer (porque está vacío o tiene un formato inválido)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<DatosErrorApi> tratarError400(HttpMessageNotReadableException excepcion) {
		DatosErrorApi datosError = new DatosErrorApi(400, HttpStatus.BAD_REQUEST, excepcion, null);
		return ResponseEntity.badRequest().body(datosError);
	}

	// TODO: Información repetida, cuando debe ser única
	// 400
	// JdbcSQLIntegrityConstraintViolationException

	// TODO: El id de la entidad original no existe
	// 404
	// EntityNotFoundException

	// TODO: El id de la entidad padre no existe
	// 400
	// JdbcSQLIntegrityConstraintViolationException

	// TODO: No pertenece a la entidad padre del id dado
	// 400
	// JdbcSQLIntegrityConstraintViolationException

	// TODO: No hay una entidad padre para la lista que se pide
	// 400
	// Sin excepción nativa

	// Maneja los casos en que no se encuetra el recurso solicitado
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> tratarError404() {
		return ResponseEntity.notFound().build();
	}

	// TODO: Usar un método inválido para un endpoint
	// 405
	// HttpRequestMethodNotSupportedException

	// TODO: Formato requerido en la petición no es soportado
	// 406
	// HttpMediaTypeNotAcceptableException

	// TODO: Formato proporcionado en la petición no es soportado
	// 415
	// HttpMediaTypeNotSupportedException

	// TODO: Error del servidor
	// 500
	// Exception
}