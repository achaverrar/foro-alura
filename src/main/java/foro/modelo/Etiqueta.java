package foro.modelo;

import java.util.ArrayList;
import java.util.List;

import foro.excepciones.InfoExcepcionesPersonalizadas;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Entity(name = "Etiqueta")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "etiquetas", uniqueConstraints = { @UniqueConstraint(columnNames = { "nombre" },
name = InfoExcepcionesPersonalizadas.CATEGORIA_NOMBRE_DUPLICADO) })
public class Etiqueta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column
	@Enumerated(EnumType.ORDINAL)
	private Nivel nivel;

	@ManyToOne
	@JoinColumn(name = "etiqueta_padre_id")
	private Etiqueta etiquetaPadre;

	@OneToMany(mappedBy="etiquetaPadre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Etiqueta> etiquetasHijas = new ArrayList<>();

	@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Publicacion> publicaciones = new ArrayList<>();
}