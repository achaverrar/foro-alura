package foro.seguridad;

public record DatosTokensIngreso(
		DatosCompletosToken accessToken, 
		DatosCompletosToken refreshToken
		) {
}