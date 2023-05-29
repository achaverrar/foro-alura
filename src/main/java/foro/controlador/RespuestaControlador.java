package foro.controlador;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import foro.dto.respuestas.DatosGuardarRespuesta;
import foro.dto.respuestas.DatosCompletosRespuesta;
import foro.servicios.RespuestaServicio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/publicaciones")
public class RespuestaControlador {
	@Autowired
	private RespuestaServicio respuestaServicio;

	@GetMapping("/{publicacionId}/respuestas")
	public ResponseEntity<Page<DatosCompletosRespuesta>> listarRespuestasPorPublicacionId(
			@PathVariable Long publicacionId,

			@PageableDefault(
					size = 10, 
					sort = {"fechaCreacion"}, 
					direction = Direction.ASC) Pageable paginacion) {

		var pagina = respuestaServicio.listarRespuestasPorPublicacionId(publicacionId, paginacion);
		return ResponseEntity.ok(pagina);
	}

	@PostMapping("/{publicacionId}/respuestas")
	@Transactional
	public ResponseEntity<DatosCompletosRespuesta> crearRespuesta(
			@PathVariable Long publicacionId, 
			@RequestBody @Valid DatosGuardarRespuesta datosRespuesta, 
			UriComponentsBuilder uriComponentsBuilder) {

		DatosCompletosRespuesta respuesta = respuestaServicio.crearRespuesta(publicacionId, datosRespuesta);

		Map<String, Long> parametrosUrl = new HashMap<>();

		parametrosUrl.put("publicacionId", publicacionId);
		parametrosUrl.put("respuestaId", respuesta.id());

		URI url = uriComponentsBuilder
				.path("/publicaciones/{publicacionId}/respuestas/{respuestaId}")
				.buildAndExpand(parametrosUrl)
				.toUri();
		return ResponseEntity.created(url).body(respuesta);
	}

	@PutMapping("/{publicacionId}/respuestas/{respuestaId}")
	@Transactional
	public ResponseEntity<DatosCompletosRespuesta> editarRespuesta(
			@PathVariable Long publicacionId,
			@PathVariable Long respuestaId,
			@RequestBody @Valid DatosGuardarRespuesta datosRespuesta) {

		DatosCompletosRespuesta respuesta = respuestaServicio.editarRespuesta(publicacionId, respuestaId, datosRespuesta);

		return ResponseEntity.ok(respuesta);
	}

	@PutMapping("/{publicacionId}/respuestas/{respuestaId}/solucion")
	@Transactional
	public ResponseEntity<Void> escogerRespuestaComoSolucion(
			@PathVariable Long publicacionId,
			@PathVariable Long respuestaId) {
		respuestaServicio.escogerRespuestaComoSolucion(publicacionId, respuestaId);
		return ResponseEntity.noContent().build();
	}
}