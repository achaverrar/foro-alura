package foro.controlador;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foro.dto.usuarios.DatosCambioContrasena;
import foro.dto.usuarios.DatosIngresoUsuario;
import foro.dto.usuarios.DatosRegistroUsuario;
import foro.seguridad.DatosTokensIngreso;
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
	@Transactional
	public ResponseEntity<Object> iniciarSesion(@RequestBody @Valid DatosIngresoUsuario datosUsuario) {
		Authentication autenticacionToken = new UsernamePasswordAuthenticationToken(datosUsuario.correo(), datosUsuario.contrasena());
		Authentication usuarioAutenticado = authenticationManager.authenticate(autenticacionToken);

		DatosTokensIngreso tokens = tokenServicio.generarTokensDeIngreso(usuarioAutenticado);

		return ResponseEntity.ok().body(tokens);
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

	@PutMapping("/miCuenta/contrasena")
	@Transactional
	public ResponseEntity<Object> cambiarContrasena(
			Principal principal,
			@RequestBody @Valid DatosCambioContrasena datosContrasena) {
		usuarioServicio.cambiarContrasena(principal, datosContrasena);
		return ResponseEntity.ok().body("Contraseña cambiada con éxito");
	}

	@PostMapping("/salida")
	@Transactional
	public ResponseEntity<Object> cerrarSesion() {
		tokenServicio.eliminarRefreshTokenDeBD();
		SecurityContextHolder.clearContext();
		return ResponseEntity.ok().build();
	}
}