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
import foro.modelo.Publicacion;
import foro.modelo.Respuesta;
import foro.repositorio.PublicacionRepositorio;
import foro.repositorio.RespuestaRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/respuestas")
public class RespuestasControlador {
	@Autowired
	private RespuestaRepositorio respuestaRepositorio;

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	@PostMapping
	public ResponseEntity<DatosRespuesta> crearRespuesta(@RequestBody @Valid DatosNuevaRespuesta datosRespuesta, UriComponentsBuilder uriComponentsBuilder) {
		Publicacion publicacion = publicacionRepositorio.getReferenceById(datosRespuesta.publicacion_id());
		Respuesta respuesta = respuestaRepositorio.save(new Respuesta(datosRespuesta.mensaje(), publicacion));
		DatosRespuesta datosRetornoRespuesta = new DatosRespuesta(respuesta);

		URI url = uriComponentsBuilder.path("/respuestas{id}").buildAndExpand(publicacion.getId()).toUri();
		return ResponseEntity.created(url).body(datosRetornoRespuesta);
	}
}