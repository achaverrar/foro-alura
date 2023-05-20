package foro.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import foro.modelo.Usuario;
import foro.repositorio.UsuarioRepositorio;
import foro.seguridad.UsuarioSeguridad;

@Service
public class UsuarioServicio implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Override
	public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepositorio.findByCorreo(correo);
		if(usuario.isPresent()) {
			return new UsuarioSeguridad(usuario.get());
		}
		throw new UsernameNotFoundException("Credenciales inválidas");
	}

}