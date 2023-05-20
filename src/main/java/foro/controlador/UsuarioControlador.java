package foro.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foro.dto.usuarios.DatosIngresoUsuario;
import foro.dto.usuarios.DatosRegistroUsuario;
import foro.servicios.UsuarioServicio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsuarioServicio usuarioServicio;

	@PostMapping("/ingreso")
	public ResponseEntity<Object> iniciarSesion(@RequestBody @Valid DatosIngresoUsuario datosUsuario) {
		Authentication token = new UsernamePasswordAuthenticationToken(datosUsuario.correo(), datosUsuario.contrasena());
		authenticationManager.authenticate(token);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/registro")
	@Transactional
	public ResponseEntity<Object> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosUsuario) {
		usuarioServicio.registrarUsuario(datosUsuario);
		return ResponseEntity.ok().body("Registro exitoso");
	}
}