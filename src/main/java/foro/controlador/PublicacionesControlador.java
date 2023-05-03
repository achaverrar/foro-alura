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

import foro.dto.DatosActualizacionPublicacion;
import foro.dto.DatosListadoPublicaciones;
import foro.dto.DatosNuevaPublicacion;
import foro.dto.DatosRespuestaPublicacion;
import foro.modelo.Publicacion;
import foro.repositorio.PublicacionRepositorio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionesControlador {
	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	@GetMapping
	public Page<DatosListadoPublicaciones> listarPublicaciones(
			@PageableDefault(size = 1, sort = {"fechaCreacion"}, direction = Direction.DESC) Pageable paginacion) {
		return publicacionRepositorio.findAll(paginacion).map(DatosListadoPublicaciones::new);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DatosListadoPublicaciones> encontrarPublicacionPorId(@PathVariable Long id) {
		Publicacion publicacion = publicacionRepositorio.getReferenceById(id);
		return ResponseEntity.ok(new DatosListadoPublicaciones(publicacion));
	}

	@PostMapping
	public ResponseEntity<DatosRespuestaPublicacion> crearPublicacion(@RequestBody @Valid DatosNuevaPublicacion datosPublicacion, UriComponentsBuilder uriComponentsBuilder) {
		Publicacion publicacion = publicacionRepositorio.save(new Publicacion(datosPublicacion));

		DatosRespuestaPublicacion datosRespuestaPublicacion = new DatosRespuestaPublicacion(
				publicacion.getId(), 
				publicacion.getTitulo(), 
				publicacion.getMensaje(), 
				publicacion.getFechaCreacion(), 
				publicacion.getEstado());

		URI url = uriComponentsBuilder.path("/publicaciones{id}").buildAndExpand(publicacion.getId()).toUri();
		return ResponseEntity.created(url).body(datosRespuestaPublicacion);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DatosRespuestaPublicacion> editarPublicacion(@RequestBody @Valid DatosActualizacionPublicacion datosPublicacion) {
        Publicacion publicacion = publicacionRepositorio.getReferenceById(datosPublicacion.id());
        publicacion.editarPublicacion(datosPublicacion);
        return ResponseEntity.ok(new DatosRespuestaPublicacion(publicacion));
    }

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity eliminarPublicacion(@PathVariable Long id) {
		Publicacion publicacion = publicacionRepositorio.getReferenceById(id);
		publicacionRepositorio.delete(publicacion);
		return ResponseEntity.noContent().build();
	}
}