package foro.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import foro.dto.publicaciones.DatosActualizacionPublicacion;
import foro.dto.publicaciones.DatosNuevaPublicacion;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "Publicacion")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "publicaciones", uniqueConstraints = { @UniqueConstraint(columnNames = { "titulo", "mensaje" }) })
public class Publicacion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "titulo", nullable = false)
	private String titulo;

	@Column(name = "mensaje", nullable = false)
	private String mensaje;

	@Column(name = "fecha_creacion")
	private LocalDateTime fechaCreacion = LocalDateTime.now();

	@Enumerated(EnumType.STRING)
	@Column(name = "estado")
	private EstadoPublicacion estado = EstadoPublicacion.NO_RESPONDIDO;
	
	@OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Respuesta> respuestas = new ArrayList<>();

	public Publicacion(DatosNuevaPublicacion datosPublicacion) {
		this.titulo = datosPublicacion.titulo();
		this.mensaje = datosPublicacion.mensaje();
	}

	public void editarPublicacion(DatosActualizacionPublicacion datosPublicacion) {
		if(datosPublicacion.titulo() != null) {
			this.titulo = datosPublicacion.titulo();			
		}
		if(datosPublicacion.mensaje() != null) {
			this.mensaje = datosPublicacion.mensaje();			
		}
	}

	public int calcularTotalRespuestas() {
		return this.respuestas.size();
	}

// TODO: incluir el resto de atributos
//	private Usuario autor;
//	private Curso curso;
}