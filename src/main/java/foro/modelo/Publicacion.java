package foro.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import foro.excepciones.InfoExcepcionesPersonalizadas;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "publicaciones", uniqueConstraints = { @UniqueConstraint(columnNames = { "titulo", "mensaje" }, 
name = InfoExcepcionesPersonalizadas.PUBLICACION_TITULO_MENSAJE_DUPLICADO) })
public class Publicacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "curso_id")
	private Etiqueta curso;

	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario autor;

	public int calcularTotalRespuestas() {
		return this.respuestas.size();
	}

}