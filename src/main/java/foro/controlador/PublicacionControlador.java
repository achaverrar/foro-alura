package foro.controlador;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import foro.dto.publicaciones.DatosResumidosPublicacion;
import foro.dto.publicaciones.DatosGuardarPublicacion;
import foro.dto.publicaciones.DatosCompletosPublicacion;
import foro.servicios.PublicacionServicio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionControlador {

	@Autowired
	private PublicacionServicio publicacionServicio;

	@GetMapping
	public ResponseEntity<Page<DatosResumidosPublicacion>> listarPublicaciones(
			@PageableDefault(size = 25, sort = {"fechaCreacion"}, direction = Direction.DESC) Pageable paginacion) {
		var pagina = publicacionServicio.listarPublicaciones(paginacion); 
		return ResponseEntity.ok(pagina);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DatosCompletosPublicacion> encontrarPublicacionPorId(@PathVariable Long id) {
		DatosCompletosPublicacion publicacion = publicacionServicio.encontrarPublicacionPorId(id);
		return ResponseEntity.ok(publicacion);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<DatosResumidosPublicacion> crearPublicacion(@RequestBody @Valid DatosGuardarPublicacion datosPublicacion, UriComponentsBuilder uriComponentsBuilder) {
		DatosResumidosPublicacion publicacion = publicacionServicio.crearPublicacion(datosPublicacion);
		URI url = uriComponentsBuilder.path("/publicaciones{id}").buildAndExpand(publicacion.id()).toUri();
		return ResponseEntity.created(url).body(publicacion);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<DatosResumidosPublicacion> editarPublicacion(@PathVariable Long id, @RequestBody @Valid DatosGuardarPublicacion datosPublicacion) {
		DatosResumidosPublicacion publicacion = publicacionServicio.editarPublicacion(id, datosPublicacion);
        return ResponseEntity.ok(publicacion);
    }

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity eliminarPublicacion(@PathVariable Long id) {
		publicacionServicio.eliminarPublicacion(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("{publicacionId}/solucion/{respuestaId}")
	@Transactional
	public ResponseEntity solucionarPublicacion(
			@PathVariable Long publicacionId,
			@PathVariable Long respuestaId) {
		publicacionServicio.solucionarPublicacion(publicacionId, respuestaId);
		return ResponseEntity.noContent().build();
	}

}