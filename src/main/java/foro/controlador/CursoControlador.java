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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import foro.dto.etiquetas.cursos.DatosCompletosCurso;
import foro.dto.etiquetas.cursos.DatosGuardarCurso;
import foro.servicios.EtiquetaServicio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/cursos")
public class CursoControlador {

	@Autowired
	private EtiquetaServicio etiquetaServicio;

	@GetMapping
	public ResponseEntity<Page<Record>> listarCursos(
			@RequestParam(name = "categoria", required = false)
			Long categoriaId,

			@RequestParam(name = "subcategoria", required = false)
			Long subcategoriaId,

			@PageableDefault(size = 25)
			Pageable paginacion) {

		Page<Record> pagina = null;

		if(categoriaId != null) {
			pagina = etiquetaServicio.listarCursosPorCategoriaId(categoriaId, paginacion); 			

		} else if(subcategoriaId != null) {
			pagina = etiquetaServicio.listarCursosPorSubcategoriaId(subcategoriaId, paginacion); 			

		} else {
			pagina = etiquetaServicio.listarCursos(paginacion);
		}

		return ResponseEntity.ok(pagina);
	}

	@GetMapping("/{cursoId}")
	public ResponseEntity<DatosCompletosCurso> encontrarCursoPorId(@PathVariable Long cursoId) {
		DatosCompletosCurso curso = etiquetaServicio.encontrarCursoPorId(cursoId);
		return ResponseEntity.ok(curso);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<DatosCompletosCurso> crearCurso(
			@RequestBody
			@Valid
			DatosGuardarCurso datosCurso, 

			UriComponentsBuilder uriComponentsBuilder) {

		DatosCompletosCurso curso = etiquetaServicio.crearCurso(datosCurso);

		URI url = uriComponentsBuilder.path("cursos/{cursoId}").buildAndExpand(curso.id()).toUri();

		return ResponseEntity.created(url).body(curso);
	}

	@PutMapping("/{cursoId}")
	@Transactional
	public ResponseEntity<DatosCompletosCurso> editarCurso(
			@PathVariable
			Long cursoId,

			@RequestBody
			@Valid
			DatosGuardarCurso datosCurso) {

		DatosCompletosCurso curso = etiquetaServicio.editarCurso(cursoId, datosCurso);

		return ResponseEntity.ok(curso);
	}

	@DeleteMapping("/{cursoId}")
	@Transactional
	public ResponseEntity<Void> eliminarCurso(@PathVariable Long cursoId) {
		etiquetaServicio.eliminarCurso(cursoId);
		return ResponseEntity.noContent().build();
	}

}