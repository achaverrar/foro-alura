package foro.dto.usuarios;

import foro.modelo.Usuario;

public record DatosResumidosUsuario(
		Long usuarioId,
		String nombre,
		String correo) {

	public DatosResumidosUsuario(Usuario usuario) {
		this(usuario.getId(), usuario.getNombre(), usuario.getCorreo());
	}
}