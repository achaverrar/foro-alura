package foro.dto;

import foro.modelo.Publicacion;
import jakarta.validation.constraints.NotBlank;

public record DatosNuevaPublicacion(
		@NotBlank(message = "El título es obligatorio")
		String titulo, 
		
		@NotBlank(message = "El mensaje es obligatorio")
		String mensaje
		) {

	public DatosNuevaPublicacion(Publicacion publicacion) {
		this(publicacion.getTitulo(), publicacion.getMensaje());
	}

}