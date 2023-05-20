package foro.dto.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosRegistroUsuario(
		@NotBlank(message = "El nombre es obligatorio")
		String nombre,

		@NotBlank(message = "La dirección de correo electrónico es obligatoria")
		@Email(message = "La dirección de correo electrónico no es válida")
		String correo,

		@NotBlank(message = "La contraseña es obligatoria")
		String contrasena
		) {
}