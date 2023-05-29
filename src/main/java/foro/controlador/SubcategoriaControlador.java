package foro.controlador;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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

import foro.dto.etiquetas.subcategorias.DatosCompletosSubcategoria;
import foro.dto.etiquetas.subcategorias.DatosGuardarSubcategoria;
import foro.dto.etiquetas.subcategorias.DatosListadoSubcategoria;
import foro.servicios.EtiquetaServicio;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/subcategorias")
public class SubcategoriaControlador {

	@Autowired
	private EtiquetaServicio etiquetaServicio;

	@GetMapping
	public ResponseEntity<Page<Record>> listarSubcategorias(
			@RequestParam(name = "categoria", required = false)
			Long categoriaId,

			@PageableDefault(size = 25)
			Pageable paginacion) {

		Page<Record> pagina = null;

		if(categoriaId != null) {
			pagina = etiquetaServicio.listarSubcategoriasPorCategoriaId(categoriaId, paginacion); 			

		} else {
			pagina = etiquetaServicio.listarSubcategorias(paginacion);
		}

		return ResponseEntity.ok(pagina);
	}

	@GetMapping("/{subcategoriaId}")
	public ResponseEntity<DatosCompletosSubcategoria> encontrarSubcategoriaPorId(@PathVariable Long subcategoriaId) {
		DatosCompletosSubcategoria subcategoria = etiquetaServicio.encontrarSubcategoriaPorId(subcategoriaId);
		return ResponseEntity.ok(subcategoria);
	}

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

	@PutMapping("/{subcategoriaId}")
	@Transactional
	public ResponseEntity<DatosListadoSubcategoria> editarSubcategoria(
			@PathVariable 
			Long subcategoriaId,

			@RequestBody
			@Valid
			DatosGuardarSubcategoria datosSubcategoria) {

		DatosListadoSubcategoria subcategoria = etiquetaServicio.editarSubcategoria(subcategoriaId, datosSubcategoria);

		return ResponseEntity.ok(subcategoria);
	}

	@DeleteMapping("/{subcategoriaId}")
	@Transactional
	public ResponseEntity<Void> eliminarSubcategoria(@PathVariable Long subcategoriaId) {
		etiquetaServicio.eliminarSubcategoria(subcategoriaId);
		return ResponseEntity.noContent().build();
	}

}