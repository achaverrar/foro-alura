package foro.dto.publicaciones;

import foro.modelo.Publicacion;
import jakarta.validation.constraints.NotBlank;

public record DatosCrearPublicacion(
		@NotBlank(message = "El título es obligatorio")
		String titulo, 
		
		@NotBlank(message = "El mensaje es obligatorio")
		String mensaje
		) {

	public DatosCrearPublicacion(Publicacion publicacion) {
		this(publicacion.getTitulo(), publicacion.getMensaje());
	}

}