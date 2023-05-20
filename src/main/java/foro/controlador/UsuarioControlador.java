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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/ingreso")
	public ResponseEntity<Object> iniciarSesion(@RequestBody @Valid DatosIngresoUsuario datosUsuario) {
		Authentication token = new UsernamePasswordAuthenticationToken(datosUsuario.correo(), datosUsuario.contrasena());
		authenticationManager.authenticate(token);
		return ResponseEntity.ok().build();
	}
}