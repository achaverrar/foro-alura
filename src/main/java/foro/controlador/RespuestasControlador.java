package foro.controlador;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import foro.dto.respuestas.DatosNuevaRespuesta;
import foro.dto.respuestas.DatosCompletosRespuesta;
import foro.servicios.RespuestaServicio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/respuestas")
public class RespuestasControlador {
	@Autowired
	private RespuestaServicio respuestaServicio;

	@GetMapping("/publicaciones/{publicacionId}")
	public ResponseEntity<Page<DatosCompletosRespuesta>> listarRespuestasPorPublicacionId(@PathVariable(name="publicacionId") Long id,
			@PageableDefault(size = 10, sort = {"fechaCreacion"}, direction = Direction.ASC) Pageable paginacion) {
		var pagina = respuestaServicio.listarRespuestasPorPublicacionId(id, paginacion);
		return ResponseEntity.ok(pagina);
	}

	@PostMapping
	public ResponseEntity<DatosCompletosRespuesta> crearRespuesta(@RequestBody @Valid DatosNuevaRespuesta datosRespuesta, UriComponentsBuilder uriComponentsBuilder) {
		DatosCompletosRespuesta respuesta = respuestaServicio.crearRespuesta(datosRespuesta);

		URI url = uriComponentsBuilder.path("/respuestas{id}").buildAndExpand(respuesta.id()).toUri();
		return ResponseEntity.created(url).body(respuesta);
	}

}