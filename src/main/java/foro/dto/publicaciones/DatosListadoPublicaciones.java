package foro.dto.publicaciones;

import java.time.LocalDateTime;

import foro.modelo.EstadoPublicacion;
import foro.modelo.Publicacion;

public record DatosListadoPublicaciones(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, EstadoPublicacion estado) {

	public DatosListadoPublicaciones(Publicacion publicacion) {
		this(publicacion.getId(), publicacion.getTitulo(), publicacion.getMensaje(), publicacion.getFechaCreacion(), publicacion.getEstado());
	}
}
