package foro.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import foro.dto.usuarios.DatosRegistroUsuario;
import foro.modelo.Rol;
import foro.modelo.Usuario;
import foro.repositorio.RolRepositorio;
import foro.repositorio.UsuarioRepositorio;
import foro.seguridad.UsuarioSeguridad;

@Service
public class UsuarioServicio implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private RolRepositorio rolRepositorio;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepositorio.findByCorreo(correo);
		if (usuario.isPresent()) {
			return new UsuarioSeguridad(usuario.get());
		}
		throw new UsernameNotFoundException("Credenciales inválidas");
	}

	public void registrarUsuario(DatosRegistroUsuario datosUsuario) {
		String correo = datosUsuario.correo();
		if (usuarioRepositorio.existsByCorreo(correo)) {
			throw new RuntimeException("El correo electrónico ingresado ya se encuentra en uso");
		}

		Usuario usuario = new Usuario();
		usuario.setNombre(datosUsuario.nombre());
		usuario.setCorreo(correo);
		usuario.setContrasena(passwordEncoder.encode(datosUsuario.contrasena()));

		Rol rol = rolRepositorio.findByNombre("USUARIO").get();
		usuario.setRoles(List.of(rol));

		usuarioRepositorio.save(usuario);
	}
}