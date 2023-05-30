package foro.controlador;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foro.dto.usuarios.DatosCambioContrasena;
import foro.servicios.UsuarioServicio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioControlador {

	@Autowired
	private UsuarioServicio usuarioServicio;

	@PostMapping("/{usuarioId}/roles/{rolId}")
	@Transactional
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> asignarRolAUsuario(
			@PathVariable Long usuarioId,
			@PathVariable Long rolId) {
		usuarioServicio.asignarRolAUsuario(usuarioId, rolId);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/contrasena")
	@Transactional
	public ResponseEntity<Object> cambiarContrasena(
			Principal principal,
			@RequestBody @Valid DatosCambioContrasena datosContrasena) {
		usuarioServicio.cambiarContrasena(principal, datosContrasena);
		return ResponseEntity.ok().body("Contraseña cambiada con éxito");
	}

}