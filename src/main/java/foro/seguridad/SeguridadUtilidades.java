package foro.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import foro.modelo.Publicacion;
import foro.modelo.Respuesta;
import foro.modelo.Usuario;
import foro.repositorio.PublicacionRepositorio;
import foro.repositorio.RespuestaRepositorio;

@Component
public class SeguridadUtilidades {

	@Autowired
	PublicacionRepositorio publicacionRepositorio;

	@Autowired
	RespuestaRepositorio respuestaRepositorio;

	public static Usuario getUsuarioAutenticado() {
		var datosAutenticacion = SecurityContextHolder.getContext().getAuthentication();
		UsuarioSeguridad usuarioSeguridad = (UsuarioSeguridad) datosAutenticacion.getPrincipal();
		return usuarioSeguridad.getUsuario();
	}

	public boolean esAutor(Long recursoId, String clase) {
		String correoAutorAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();

		if(Publicacion.class.getSimpleName().equals(clase)) {

			Publicacion publicacion = publicacionRepositorio.getReferenceById(recursoId);
			String correoAutorPublicacion = publicacion.getAutor().getCorreo(); 
			return correoAutorAutenticado.equals(correoAutorPublicacion);

		} else if (Respuesta.class.getSimpleName().equals(clase)) {

			Respuesta respuesta = respuestaRepositorio.getReferenceById(recursoId);
			 String correoAutorRespuesta = respuesta.getAutor().getCorreo(); 

			return correoAutorAutenticado.equals(correoAutorRespuesta);
		}
		return false;
	}
}