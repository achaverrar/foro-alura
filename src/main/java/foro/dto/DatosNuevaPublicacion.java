package foro.dto;

import foro.modelo.Publicacion;

public record DatosNuevaPublicacion(String titulo, String mensaje) {
	public DatosNuevaPublicacion(Publicacion publicacion) {
		this(publicacion.getTitulo(), publicacion.getMensaje());
	}
}