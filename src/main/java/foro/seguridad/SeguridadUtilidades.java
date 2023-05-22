package foro.seguridad;

import org.springframework.security.core.context.SecurityContextHolder;

import foro.modelo.Usuario;

public class SeguridadUtilidades {

	public static Usuario getUsuarioAutenticado() {
		var datosAutenticacion = SecurityContextHolder.getContext().getAuthentication();
		UsuarioSeguridad usuarioSeguridad = (UsuarioSeguridad) datosAutenticacion.getPrincipal();
		return usuarioSeguridad.getUsuario();
	}
}