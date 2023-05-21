package foro.dto.usuarios;

import jakarta.validation.constraints.NotBlank;

public record DatosCambioContrasena(
		@NotBlank(message = "La contraseña actual es obligatoria")
		String contrasenaActual,

		@NotBlank(message = "La nueva contraseña es obligatoria")
		String contrasenaNueva,

		@NotBlank(message = "La confirmación de la contraseña es obligatoria")
		String contrasenaConfirmacion) {
}