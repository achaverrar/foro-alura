package foro.dto.respuestas;

import java.time.LocalDateTime;

import foro.modelo.Respuesta;

public record DatosCompletosRespuesta(Long id, String mensaje, LocalDateTime fechaCreacion, Boolean solucion, Long publicacion_id) {

	public DatosCompletosRespuesta(Respuesta respuesta) {
		this(respuesta.getId(), respuesta.getMensaje(), respuesta.getFechaCreacion(), respuesta.getSolucion(), respuesta.getPublicacion().getId());		
	}
}