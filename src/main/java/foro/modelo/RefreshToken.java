package foro.modelo;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "Refresh_Token")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "token", nullable = false)
	private String token;

	@Column(name = "fecha_expiracion", nullable = false)
	private LocalDateTime fechaExpiracion;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Usuario usuario;

	public Date getFechaExpiracion() {
		return Date.from(this.fechaExpiracion.atZone(ZoneId.systemDefault()).toInstant());
	}
}