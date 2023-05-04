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

import foro.dto.publicaciones.DatosActualizacionPublicacion;
import foro.dto.publicaciones.DatosListadoPublicaciones;
import foro.dto.publicaciones.DatosNuevaPublicacion;
import foro.dto.publicaciones.DatosRespuestaPublicacion;
import foro.servicios.PublicacionServicio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionesControlador {

	@Autowired
	private PublicacionServicio publicacionServicio;

	@GetMapping
	public ResponseEntity<Page<DatosListadoPublicaciones>> listarPublicaciones(
			@PageableDefault(size = 1, sort = {"fechaCreacion"}, direction = Direction.DESC) Pageable paginacion) {
		var pagina = publicacionServicio.listarPublicaciones(paginacion); 
		return ResponseEntity.ok(pagina);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DatosListadoPublicaciones> encontrarPublicacionPorId(@PathVariable Long id) {
		DatosListadoPublicaciones publicacion = publicacionServicio.encontrarPublicacionPorId(id);
		return ResponseEntity.ok(publicacion);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<DatosRespuestaPublicacion> crearPublicacion(@RequestBody @Valid DatosNuevaPublicacion datosPublicacion, UriComponentsBuilder uriComponentsBuilder) {
		DatosRespuestaPublicacion publicacion = publicacionServicio.crearPublicacion(datosPublicacion);
		URI url = uriComponentsBuilder.path("/publicaciones{id}").buildAndExpand(publicacion.id()).toUri();
		return ResponseEntity.created(url).body(publicacion);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DatosRespuestaPublicacion> editarPublicacion(@RequestBody @Valid DatosActualizacionPublicacion datosPublicacion) {
		DatosRespuestaPublicacion publicacion = publicacionServicio.editarPublicacion(datosPublicacion);
        return ResponseEntity.ok(publicacion);
    }

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity eliminarPublicacion(@PathVariable Long id) {
		publicacionServicio.eliminarPublicacion(id);
		return ResponseEntity.noContent().build();
	}
}