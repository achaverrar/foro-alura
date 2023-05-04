package foro.controlador;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import foro.dto.respuestas.DatosNuevaRespuesta;
import foro.dto.respuestas.DatosRespuesta;
import foro.servicios.RespuestaServicio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/respuestas")
public class RespuestasControlador {
	@Autowired
	private RespuestaServicio respuestaServicio;

	@PostMapping
	public ResponseEntity<DatosRespuesta> crearRespuesta(@RequestBody @Valid DatosNuevaRespuesta datosRespuesta, UriComponentsBuilder uriComponentsBuilder) {
		DatosRespuesta respuesta = respuestaServicio.crearRespuesta(datosRespuesta);

		URI url = uriComponentsBuilder.path("/respuestas{id}").buildAndExpand(respuesta.id()).toUri();
		return ResponseEntity.created(url).body(respuesta);
	}

}