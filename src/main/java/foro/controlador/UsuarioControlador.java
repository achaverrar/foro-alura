package foro.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foro.dto.usuarios.DatosIngresoUsuario;
import foro.dto.usuarios.DatosRegistroUsuario;
import foro.seguridad.DatosToken;
import foro.seguridad.TokenServicio;
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

	@Autowired
	private TokenServicio tokenServicio;

	@PostMapping("/ingreso")
	public ResponseEntity<Object> iniciarSesion(@RequestBody @Valid DatosIngresoUsuario datosUsuario) {
		Authentication autenticacionToken = new UsernamePasswordAuthenticationToken(datosUsuario.correo(), datosUsuario.contrasena());
		Authentication usuarioAutenticado = authenticationManager.authenticate(autenticacionToken);
		String jwToken = tokenServicio.generarToken(usuarioAutenticado);
		return ResponseEntity.ok().body(new DatosToken(jwToken));
	}

	@PostMapping("/registro")
	@Transactional
	public ResponseEntity<Object> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosUsuario) {
		usuarioServicio.registrarUsuario(datosUsuario);
		return ResponseEntity.ok().body("Registro exitoso");
	}

	@PostMapping("/{usuarioId}/roles/{rolId}")
	@Transactional
	public ResponseEntity<Object> asignarRolAUsuario(
			@PathVariable Long usuarioId,
			@PathVariable Long rolId) {
		usuarioServicio.asignarRolAUsuario(usuarioId, rolId);
		return ResponseEntity.ok().build();
	}

}