package foro.dto.publicaciones;

import java.time.LocalDateTime;

import foro.modelo.EstadoPublicacion;
import foro.modelo.Publicacion;

public record DatosResumidosPublicacion(
		Long publicacionId,
		String titulo,
		String mensaje,
		LocalDateTime fechaCreacion,
		EstadoPublicacion estado,
		int totalRespuestas,
		Long cursoId
	) {

	public DatosResumidosPublicacion(Publicacion publicacion) {
		this(
				publicacion.getId(),
				publicacion.getTitulo(),
				publicacion.getMensaje(),
				publicacion.getFechaCreacion(),
				publicacion.getEstado(),
				publicacion.calcularTotalRespuestas(),
				publicacion.getCurso().getId()
				);
	}
}