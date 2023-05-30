package foro.modelo;

import org.springframework.security.core.GrantedAuthority;

import foro.excepciones.InfoExcepcionesPersonalizadas;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "Rol")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "roles", uniqueConstraints = { @UniqueConstraint(columnNames = { "nombre" }, 
name = InfoExcepcionesPersonalizadas.ROL_NOMBRE_DUPLICADO) })
public class Rol implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	@Override
	public String getAuthority() {
		return this.nombre;
	}
}