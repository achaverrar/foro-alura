package foro.excepciones;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import foro.dto.excepciones.DatosErrorApi;
import foro.dto.excepciones.DatosErrorIdDeEntidadInvalido;
import foro.dto.excepciones.DatosErrorPertenenciaInvalida;
import foro.dto.excepciones.DatosErrorRecursoNoEncontrado;
import foro.dto.excepciones.DatosErrorValidacion;
import foro.dto.excepciones.DatosErrorValorDuplicado;
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

	// Maneja las peticiones que contienen datos duplicados, cuando debe ser únicos
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<DatosErrorValorDuplicado> tratarError400(DataIntegrityViolationException excepcion) {
		DatosErrorValorDuplicado datosError = new DatosErrorValorDuplicado(excepcion);
		return ResponseEntity.badRequest().body(datosError);		
	}

	// Maneja las excepciones por transacciones sobre entidades inexistentes
	@ExceptionHandler(TransaccionSobreEntidadInexistenteException.class)
	public ResponseEntity<DatosErrorIdDeEntidadInvalido> tratarError400(TransaccionSobreEntidadInexistenteException excepcion) {
		DatosErrorIdDeEntidadInvalido datosError = new DatosErrorIdDeEntidadInvalido(excepcion);
		return ResponseEntity.badRequest().body(datosError);
	}

	// Maneja las excepciones en las que la relación entre entidades es inválida
	// Ejemplo: se intenta modificar una publicación que existe, pero no en el curso del id dado
	@ExceptionHandler(PertenenciaInvalidaExcepcion.class)
	public ResponseEntity<DatosErrorPertenenciaInvalida> tratarError400(PertenenciaInvalidaExcepcion excepcion) {
		DatosErrorPertenenciaInvalida datosError = new DatosErrorPertenenciaInvalida(excepcion);
		return ResponseEntity.badRequest().body(datosError);
	}

	// Maneja los casos en que no se encuentra el recurso solicitado
	@ExceptionHandler(RecursoNoEncontradoException.class)
	public ResponseEntity<DatosErrorRecursoNoEncontrado> tratarError404(RecursoNoEncontradoException excepcion) {
		DatosErrorRecursoNoEncontrado datosError = new DatosErrorRecursoNoEncontrado(excepcion);
		return ResponseEntity.status(datosError.status()).body(datosError);
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