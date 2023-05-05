package foro.dto.publicaciones;

import java.time.LocalDateTime;

import foro.modelo.EstadoPublicacion;
import foro.modelo.Publicacion;

public record DatosResumidosPublicacion(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, EstadoPublicacion estado) {

	public DatosResumidosPublicacion(Publicacion publicacion) {
		this(publicacion.getId(), publicacion.getTitulo(), publicacion.getMensaje(), publicacion.getFechaCreacion(), publicacion.getEstado());
	}
}
