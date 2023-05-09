package foro.excepciones;

import java.util.Map;

public class InfoExcepcionesPersonalizadas {

	public static final String CURSO_NOMBRE_DUPLICADO = "CURSO_NOMBRE_DUPLICADO";

	public static final Map<String, String> MENSAJES = Map.of(
			CURSO_NOMBRE_DUPLICADO, "El nombre del curso ya existe"
			);

}