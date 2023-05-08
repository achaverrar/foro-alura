package foro.controlador;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import foro.dto.cursos.DatosGuardarCurso;
import foro.dto.cursos.DatosResumidosCurso;
import foro.servicios.CursoServicio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cursos")
public class CursoControlador {

	@Autowired
	private CursoServicio cursoServicio;

	@GetMapping
	public ResponseEntity<Page<DatosResumidosCurso>> listarCursos(@PageableDefault(size = 25) Pageable paginacion) {
		var pagina = cursoServicio.listarCursos(paginacion); 
		return ResponseEntity.ok(pagina);
	}

	@GetMapping("/{cursoId}")
	public ResponseEntity<DatosResumidosCurso> encontrarCursoPorId(@PathVariable Long cursoId) {
		DatosResumidosCurso curso = cursoServicio.encontrarCursoPorId(cursoId);
		return ResponseEntity.ok(curso);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<DatosResumidosCurso> crearCurso(
			@RequestBody
			@Valid
			DatosGuardarCurso datosCurso, 

			UriComponentsBuilder uriComponentsBuilder) {

		DatosResumidosCurso curso = cursoServicio.crearCurso(datosCurso);

		URI url = uriComponentsBuilder.path("cursos/{cursoId}").buildAndExpand(curso.id()).toUri();

		return ResponseEntity.created(url).body(curso);
	}

	@PutMapping("/{cursoId}")
	@Transactional
	public ResponseEntity<DatosResumidosCurso> editarCurso(
			@PathVariable
			Long cursoId,

			@RequestBody
			@Valid
			DatosGuardarCurso datosCurso) {

		DatosResumidosCurso curso = cursoServicio.editarCurso(cursoId, datosCurso);

		return ResponseEntity.ok(curso);
	}

	@DeleteMapping("/{cursoId}")
	@Transactional
	public ResponseEntity<Void> eliminarCurso(@PathVariable Long cursoId) {
		cursoServicio.eliminarPublicacion(cursoId);
		return ResponseEntity.noContent().build();
	}
}