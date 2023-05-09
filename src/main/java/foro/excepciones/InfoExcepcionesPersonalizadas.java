package foro.excepciones;

import java.util.Map;

public class InfoExcepcionesPersonalizadas {

	public static final String CURSO_NOMBRE_DUPLICADO = "CURSO_NOMBRE_DUPLICADO";
	public static final String PUBLICACION_TITULO_MENSAJE_DUPLICADO = "PUBLICACION_TITULO_MENSAJE_DUPLICADO";
	public static final String RESPUESTA_MENSAJE_DUPLICADO = "RESPUESTA_MENSAJE_DUPLICADO";

	public static final Map<String, String> MENSAJES = Map.of(
			CURSO_NOMBRE_DUPLICADO, "El nombre del curso ya existe",
			PUBLICACION_TITULO_MENSAJE_DUPLICADO, "Ya existe una publicación con este mensaje",
			RESPUESTA_MENSAJE_DUPLICADO, "Esta respuesta ya existe"
			);

}