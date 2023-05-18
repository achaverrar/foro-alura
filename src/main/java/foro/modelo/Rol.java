package foro.modelo;

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
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nombre;
}