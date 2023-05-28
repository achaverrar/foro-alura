package foro.controlador;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import foro.dto.etiquetas.subcategorias.DatosGuardarSubcategoria;
import foro.dto.etiquetas.subcategorias.DatosListadoSubcategoria;
import foro.servicios.EtiquetaServicio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/subcategorias")
public class SubcategoriaControlador {

	@Autowired
	private EtiquetaServicio etiquetaServicio;

	@PostMapping
	@Transactional
	public ResponseEntity<DatosListadoSubcategoria> crearSubcategoria(
			@RequestBody
			@Valid
			DatosGuardarSubcategoria datosSubcategoria,

			UriComponentsBuilder uriComponentsBuilder) {

		DatosListadoSubcategoria subcategoria = etiquetaServicio.crearSubcategoria(datosSubcategoria);

		URI url = uriComponentsBuilder.path("subcategorias/{subcategoriaId}").buildAndExpand(subcategoria.id()).toUri();

		return ResponseEntity.created(url).body(subcategoria);
	}
}