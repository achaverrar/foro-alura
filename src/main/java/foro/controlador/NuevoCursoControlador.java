package foro.controlador;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import foro.dto.etiquetas.cursos.DatosCompletosCurso;
import foro.dto.etiquetas.cursos.DatosGuardarCurso;
import foro.servicios.EtiquetaServicio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cursos")
public class NuevoCursoControlador {

	@Autowired
	private EtiquetaServicio etiquetaServicio;

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
}