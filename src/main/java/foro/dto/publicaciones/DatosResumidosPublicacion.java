package foro.dto.publicaciones;

import java.time.LocalDateTime;

import foro.dto.usuarios.DatosResumidosUsuario;
import foro.modelo.EstadoPublicacion;
import foro.modelo.Publicacion;

public record DatosResumidosPublicacion(
		Long publicacionId,
		String titulo,
		String mensaje,
		LocalDateTime fechaCreacion,
		EstadoPublicacion estado,
		int totalRespuestas,
		Long cursoId,
		DatosResumidosUsuario usuario) {

	public DatosResumidosPublicacion(Publicacion publicacion) {
		this(
				publicacion.getId(),
				publicacion.getTitulo(),
				publicacion.getMensaje(),
				publicacion.getFechaCreacion(),
				publicacion.getEstado(),
				publicacion.calcularTotalRespuestas(),
				publicacion.getCurso().getId(),
				new DatosResumidosUsuario(publicacion.getAutor()));
	}
}