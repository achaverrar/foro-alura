package foro.modelo;

import java.util.ArrayList;
import java.util.List;

import foro.excepciones.InfoExcepcionesPersonalizadas;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "Usuario")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "usuarios", uniqueConstraints = { @UniqueConstraint(columnNames = { "correo" }, 
name = InfoExcepcionesPersonalizadas.USUARIO_CORREO_DUPLICADO) })
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;
	private String correo;
	private String contrasena;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="usuarios_roles", 
	joinColumns = @JoinColumn(name="usuario_id", referencedColumnName = "id"),
    inverseJoinColumns=@JoinColumn(name="rol_id", referencedColumnName = "id")) 
	private List<Rol> roles = new ArrayList<>();

	@OneToMany(mappedBy = "autor")
	private List<Publicacion> publicaciones = new ArrayList<>();
	
	@OneToMany(mappedBy = "autor")
	private List<Respuesta> respuestas = new ArrayList<>();
}