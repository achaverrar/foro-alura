package foro.modelo;

import java.time.LocalDateTime;

import foro.excepciones.InfoExcepcionesPersonalizadas;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "Respuesta")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "respuestas", uniqueConstraints = { @UniqueConstraint(columnNames = { "mensaje", "publicacion_id" }, 
name = InfoExcepcionesPersonalizadas.RESPUESTA_MENSAJE_DUPLICADO) })
public class Respuesta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String mensaje;
	private LocalDateTime fechaCreacion = LocalDateTime.now();
	private Boolean solucion = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "publicacion_id", nullable = false)
	private Publicacion publicacion;

	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario autor;
}