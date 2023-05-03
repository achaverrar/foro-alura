package foro.dto;

import foro.modelo.Publicacion;
import jakarta.validation.constraints.NotBlank;

public record DatosNuevaPublicacion(@NotBlank String titulo, @NotBlank String mensaje) {
	public DatosNuevaPublicacion(Publicacion publicacion) {
		this(publicacion.getTitulo(), publicacion.getMensaje());
	}
}