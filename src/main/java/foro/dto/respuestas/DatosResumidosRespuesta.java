package foro.dto.respuestas;

import java.time.LocalDateTime;

import foro.modelo.Respuesta;

public record DatosResumidosRespuesta(Long id, String mensaje, LocalDateTime fechaCreacion, Boolean solucion) {

	public DatosResumidosRespuesta(Respuesta respuesta) {
		this(respuesta.getId(), respuesta.getMensaje(), respuesta.getFechaCreacion(), respuesta.getSolucion());		
	}
}