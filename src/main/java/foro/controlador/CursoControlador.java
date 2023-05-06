package foro.controlador;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import foro.dto.cursos.DatosGuardarCurso;
import foro.dto.cursos.DatosResumidosCurso;
import foro.servicios.CursoServicio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cursos")
public class CursoControlador {

	@Autowired
	private CursoServicio cursoServicio;

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
}