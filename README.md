# Challenge ONE | Back End | Foro Alura

Esta es mi solución al último reto de la formación de Backend con Java y Spring del programa ONE. El proyecto consiste en una réplica del backend del foro de la plataforma Alura Latam, en la que todos los estudiantes de la plataforma podemos hacer preguntasy responder preguntas, colaborando e interactuando con otros estudiantes, así como también con profesores y moderadores.

## Requerimientos

Los requerimientos para la API REST son los siguientes:

- ✅ Crear una nueva publicación
- ✅ Mostrar todas las publicaciones creadas
- ✅ Mostrar una publicación específica
- ✅ Actualizar una publicación
- ✅ Eliminar una publicación

Mi proyecto cumple con todos ellos y, además cumple con los siguientes requerimientos adicionales:

- ✅ Registro, ingreso y salida de usuarios
- ✅ Contraseñas encriptadas con BCrypt Password Encoder
- ✅ Cambio de contraseña y asignar rol a usuario
- ✅ Autenticación usando JSON Web Tokens (JWT)
- ✅ Autorización basada en roles y a nivel de métodos
- ✅ Refresh Tokens
- ✅ Entidades adicionales: Rol, Refresh Tokens y Etiquetas (Categorías y Subcategorías)
- ✅ Operaciones CRUD en todas las entidades
- ✅ Escoger respuesta como solución
- ✅ Manejo excepciones con mensajes personalizados

- ## Tecnologías utilizadas:

  - [Eclipse](https://www.eclipse.org/)
  - [H2](https://www.h2database.com/)
  - [MySql](https://www.mysql.com/)
  - [Java](https://www.java.com/en/)
  - [Spring Security](https://start.spring.io/)
  - [Token JWT](https://jwt.io/)

## EndPoints

- [Autenticación](#autenticacion)
  - [Registro de usuario (Sign up)](#registro-de-usuario-sign-up)
  - [Ingreso de usuario (Login)](#ingreso-de-usuario-login)
  - [Salida de usuario (Log out)](#salida-de-usuario-log-out)
  - [Refresh Token](#refresh-token)
  - [Cambiar contraseña](#cambiar-contraseña)
  - [Asignar rol a usuario](#asignar-rol-a-usuario)
  - [Tabla Usuario](#tabla-usuario)
  - [Tabla Refresh Token](#tabla-refresh-token)
- [Roles](#roles)
  - [Crear rol](#crear-rol)
  - [Tabla Rol](#tabla-rol)
- [Etiquetas](#etiquetas)
  - [Tabla Etiqueta](#tabla-etiqueta)
- [Categorías](#categorías)
  - [Crear Categoría](#crear-categoría)
  - [Listar Categorías](#listar-categorías)
  - [Obtener Categoría](#obtener-categoría)
  - [Editar Categoría](#editar-categoría)
  - [Eliminar Categoría](#eliminar-categoría)
- [Subcategorías](#subcategorías)
  - [Crear Subcategoría](#crear-subcategoría)
  - [Listar Subcategorías](#listar-subcategorías)
  - [Listar Subcategorías por Categoría](#listar-subcategorías-por-categoría)
  - [Obtener Subcategorías por Id](#obtener-subcategorías-por-id)
  - [Editar Subcategoría](#editar-subcategoría)
  - [Eliminar Subcategoría](#eliminar-subcategoría)
- [Cursos](#cursos)
  - [Crear curso](#crear-subcategoría)
  - [Listar Cursos](#listar-cursos)
  - [Listar Cursos por Categoría](#listar-cursos-por-categoría)
  - [Listar Cursos por Subcategoría](#listar-cursos-por-subcategoría)
  - [Obtener Curso por Id](#obtener-curso-por-id)
  - [Editar Curso](#editar-subcategoría)
  - [Eliminar Curso](#eliminar-subcategoría)
- [Publicaciones](#publicaciones)
- [Respuestas](#respuestas)
- [Insignia por completar el challenge](#insignia-por-completar-el-challenge)

## Autenticación

Algunos endpoints u operaciones en endpoints requieren autenticación del tipo bearer token. Para recibir dicho token, debes registrar tu usuario e iniciar sesión. Tras lo último, recibirás dos tokens como respuesta: un access token y un refresh token.

- **Access token**: es un JWT de corta duración que debes enviar en el header de las peticiones en las que necesitas para autenticarte, así:

```
Authorization: Bearer jwt.token.aquí
```

- **Refresh token**: este token tiene una mayor duración, pero **no es un JWT**, por lo que **no es un reemplazo del access token**. Este token te sirve para generar nuevos access tokens sin necesidad de tener que iniciar sesión cada vez que tu access token expire.

| Endpoint                                   | Método | Acceso            | Descripción                                                              |
| ------------------------------------------ | ------ | ----------------- | ------------------------------------------------------------------------ |
| /api/v1/auth/signup                        | POST   | Público           | Crea un usuario en la base de datos                                      |
| /api/v1/auth/login                         | POST   | Público           | Genera par de tokens (access-refresh)                                    |
| /api/v1/auth/logout                        | POST   | Privado/Protegido | Invalida el refresh token y elimina al usuario del SecurityContextHolder |
| /api/v1/auth/token/refresh                 | PUT    | Público           | Genera un nuevo access token                                             |
| /api/v1/usuarios/{usuarioId}/roles/{rolId} | PUT    | Privado/Admin     | Asigna rol a usuario                                                     |
| /api/v1/usuarios/contrasena                | PUT    | Privado/Protegido | Cambia la contraseña                                                     |

## Registro de usuario (Sign up)

```bash
[POST] https://localhost:8080/api/v1/auth/signup
```

```json
{
  "nombre": "Fulano De Tal",
  "correo": "fulano.detal@correo.com",
  "contrasena": "admin1234"
}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
Registro exitoso
```

</details>

## Ingreso de usuario (Login)

```bash
[POST] https://localhost:8080/api/v1/auth/login
```

```json
{
  "correo": "fulano.detal@correo.com",
  "contrasena": "admin1234"
}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"accessToken": {
		"token": "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJGb3JvIEFsdXJhIiwic3ViIjoiYW5hLnNvdXphQHZvbGwubWVkIiwiZXhwIjoxNjg1MjgxMTEwfQ.WEWV8kL0oLQksYyVdkGXU66Wbi5Fu1HQMghGczb7wbsKJicUWW9VJL2oauHhTF3SXPBmpnRIBDqxtEPonPGIkw",
		"fecha_expiracion": "2023-05-28T13:38:30.095+00:00"
	},
	"refreshToken": {
		"token": "4jtstmqo31k4cp0052887h2b8s8b07ai4j4csrso3kgsqlaeg8d1hhhq7sij3a40ocdlf1oo800kquoonh6jlvd2mlmscfpdeiprv6geti9lgt35c6kpmi8u7nqoaqrv",
		"fecha_expiracion": "2023-05-29T13:08:30.092+00:00"
	}
}
```

</details>

## Salida de usuario (Log out)

```bash
[POST] https://localhost:8080/api/v1/auth/logout
```

```json
{
  "correo": "fulano.detal@correo.com",
  "contrasena": "admin1234"
}
```

<details><summary><b>Output</b></summary>
<br/>

> Respuesta sin cuerpo

</details>

## Refresh Token

```bash
[POST] https://localhost:8080/api/v1/auth/token/refresh
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"token": "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJGb3JvIEFsdXJhIiwic3ViIjoiYW5hLnNvdXphQHZvbGwubWVkIiwiZXhwIjoxNjg1MjAwMjQ3fQ.lG8CwawuAAI4xMLDbWSBZfwsdUDnjuXogu--4_cDohLQe4wFUuqnFWK7UNPcWHy9dvZ5kgMSMxtdUw2owW75lg",
	"fecha_expiracion": "2023-05-27T15:10:47.470+00:00"
}
```

> Este es un access token

</details>

## Cambiar contraseña

```bash
[PUT] https://localhost:8080/api/v1/usuarios/contrasena
```

```json
{
  "contrasenaActual": "admin1234",
  "contrasenaNueva": "user1234",
  "contrasenaConfirmacion": "user1234"
}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
Contraseña cambiada con éxito
```

</details>

## Asignar rol a usuario

```bash
[PUT] https://localhost:8080/api/v1/usuarios/{usuarioId}/roles/{rolId}
```

<details><summary><b>Output</b></summary>
<br/>
> Respuesta sin cuerpo

</details>

## Tabla Usuario

| Attribute     | Type   |
| ------------- | ------ |
| id            | long   |
| nombre        | string |
| correo        | string |
| contrasena    | string |
| roles         | array  |
| publicaciones | array  |
| respuestas    | array  |

## Tabla Refresh Token

| Attribute        | Type   |
| ---------------- | ------ |
| id               | long   |
| token            | string |
| fecha_expiracion | date   |
| usuario_id       | long   |

---

## Roles

#### Endpoints para roles

---

## Crear rol

```bash
[POST] https://localhost:8080/api/v1/roles
```

```json
{
  "nombre": "USUARIO"
}
```

<details><summary><b>Output</b></summary>
<br/>

> Respuesta sin cuerpo

</details>

## Tabla Rol

| Attribute     | Type   |
| ------------- | ------ |
| id            | long   |
| nombre        | string |
| correo        | string |
| contrasena    | string |
| roles         | array  |
| publicaciones | array  |
| respuestas    | array  |

---

## Etiquetas

#### Generalización de Categorías, Subcategorías y Cursos

---

En el Foro Alura original, las publicaciones están organizadas por categorías, subcategorías y cursos. La información de estas tres entidades es similar y puede modelarse en una única entidad para evitar redundancia en la base de datos.

Por eso, en mi proyecto, tengo una entidad llamada Etiquetas, en lugar de las otras tres: Categorías, Subcategorías y Cursos. Sin embargo, atiendo las peticiones de cada una en un controlador separado.

## Tabla Etiqueta

| Attribute       | Type   |
| --------------- | ------ |
| id              | long   |
| nombre          | string |
| nivel           | int    |
| etiqueta_padre  | array  |
| etiquetas_hijas | array  |
| publicaciones   | array  |

---

## Categorías

#### Endpoints para Categorías

---

| Enpoint                          | Método | Acceso        | Descripción              |
| -------------------------------- | ------ | ------------- | ------------------------ |
| /api/v1/categorias/              | POST   | Privado/Admin | Crear categoría          |
| /api/v1/categorias/              | GET    | Público       | Listar Categorías        |
| /api/v1/categorias/{categoriaId} | GET    | Público       | Obtener categoría por id |
| /api/v1/categorias/{categoriaId} | PUT    | Privado/Admin | Editar categoría         |
| /api/v1/categorias/{categoriaId} | DELETE | Privado/Admin | Eliminar categoría       |

## Crear Categoría

```bash
[POST] https://localhost:8080/api/v1/categorias
```

```json
{
  "nombre": "Programación"
}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"id": 1,
	"nombre": "Programación"
}
```

## </details>

## Listar Categorías

```bash
[GET] https://localhost:8080/api/v1/categorias
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"content": [
		{
			"id": 1,
			"nombre": "Programación"
		},
		{
			"id": 2,
			"nombre": "Front End"
		}
	],
	"pageable": {
		"sort": {
			"empty": true,
			"sorted": false,
			"unsorted": true
		},
		"offset": 0,
		"pageNumber": 0,
		"pageSize": 25,
		"unpaged": false,
		"paged": true
	},
	"last": true,
	"totalElements": 2,
	"totalPages": 1,
	"number": 0,
	"size": 25,
	"sort": {
		"empty": true,
		"sorted": false,
		"unsorted": true
	},
	"numberOfElements": 2,
	"first": true,
	"empty": false
}
```

## </details>

## Obtener Categoría

```bash
[GET] https://localhost:8080/api/v1/categorias/{categoriaId}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"id": 1,
	"nombre": "Programación",
	"subcategorias": [
		{
			"id": 2,
			"nombre": "Todas las categorías"
		},
		{
			"id": 3,
			"nombre": "Java"
		},
		{
			"id": 4,
			"nombre": "Lógica de programación"
		}
	]
}
```

## Editar Categoría

```bash
[PUT] https://localhost:8080/api/v1/categorias/{categoriaId}
```

```json
{
  "nombre": "Nueva Categoría"
}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"id": 1,
	"nombre": "Nueva Categoría"
}
```

## </details>

## Eliminar Categoría

```bash
[DELETE] https://localhost:8080/api/v1/categorias/{categoriaId}
```

```json
{
  "nombre": "Nueva Categoría"
}
```

<details><summary><b>Output</b></summary>
<br/>

> Respuesta sin cuerpo

## </details>

---

## Subcategorías

#### Endpoints para Subcategorías

---

| Enpoint                                        | Método | Acceso        | Descripción                        |
| ---------------------------------------------- | ------ | ------------- | ---------------------------------- |
| /api/v1/subcategorias/                         | POST   | Privado/Admin | Crear subcategoría                 |
| /api/v1/subcategorias/                         | GET    | Público       | Listar subcategorías               |
| /api/v1/subcategorias/?categoria={categoriaId} | GET    | Público       | Listar subcategorías por categoria |
| /api/v1/subcategorias/{categoriaId}            | GET    | Público       | Obtener subcategoría por id        |
| /api/v1/subcategorias/{categoriaId}            | PUT    | Privado/Admin | Editar subcategoría                |
| /api/v1/subcategorias/{categoriaId}            | DELETE | Privado/Admin | Eliminar subcategoría              |

## Crear Subcategoría

```bash
[POST] https://localhost:8080/api/v1/subcategorias
```

```json
{
  "nombre": "Programación",
  "categoria_id": "Java"
}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"id": 5,
	"nombre": "HTML y CSS",
	"categoria": {
		"id": 4,
		"nombre": "Frontend"
	}
}
```

## </details>

## Listar Subcategorías

###

```bash
[GET] https://localhost:8080/api/v1/subcategorias
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
	{
	"content": [
		{
			"id": 2,
			"nombre": "Subcategoría 1",
			"categoria": {
				"id": 1,
				"nombre": "Categoría 2"
			}
		}
	],
	"pageable": {
		"sort": {
			"sorted": false,
			"unsorted": true,
			"empty": true
		},
		"pageNumber": 0,
		"pageSize": 25,
		"offset": 0,
		"paged": true,
		"unpaged": false
	},
	"last": true,
	"totalPages": 1,
	"totalElements": 1,
	"sort": {
		"sorted": false,
		"unsorted": true,
		"empty": true
	},
	"numberOfElements": 1,
	"first": true,
	"number": 0,
	"size": 25,
	"empty": false
}
```

## Listar Subcategorías por Categoría

###

```bash
[GET] https://localhost:8080/api/v1/subcategorias?categoria={categoriaId}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"content": [
		{
			"id": 2,
			"nombre": "Java"
		}
	],
	"pageable": {
		"sort": {
			"sorted": false,
			"unsorted": true,
			"empty": true
		},
		"pageNumber": 0,
		"pageSize": 25,
		"offset": 0,
		"paged": true,
		"unpaged": false
	},
	"last": true,
	"totalPages": 1,
	"totalElements": 1,
	"sort": {
		"sorted": false,
		"unsorted": true,
		"empty": true
	},
	"numberOfElements": 1,
	"first": true,
	"number": 0,
	"size": 25,
	"empty": false
}
```

## </details>

## Obtener Subcategorías por Id

###

```bash
[GET] https://localhost:8080/api/v1/subcategorias/{subcategoriaId}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"id": 2,
	"nombre": "Java",
	"categoria": {
		"id": 1,
		"nombre": "Programación"
	},
	"cursos": [
		{
			"id": 3,
			"nombre": "Lógica de Programación: Primeros Pasos"
		},
		{
			"id": 4,
			"nombre": "Lógica de Programación: Conceptos Primordiales"
		}
	]
}
```

## </details>

## Editar Subcategoría

###

```bash
[PUT] https://localhost:8080/api/v1/subcategorias/{subcategoriaId}
```

```json
{
  "nombre": "Subcategoría Modificada",
  "categoria_id": 1
}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"id": 2,
	"nombre": "Nueva Subcategoría 2",
	"categoria": {
		"id": 2,
		"nombre": "Nueva Subcategoría 2"
	}
}
```

## </details>

## Eliminar Subcategoría

###

```bash
[DELETE] https://localhost:8080/api/v1/subcategorias/{subcategoriaId}
```

<details><summary><b>Output</b></summary>
<br/>

> Respuesta sin cuerpo

## </details>

---

## Cursos

#### Endpoints para Cursos

---

| Enpoint                                       | Método | Acceso        | Descripción                    |
| --------------------------------------------- | ------ | ------------- | ------------------------------ |
| /api/v1/cursos/                               | POST   | Privado/Admin | Crear curso                    |
| /api/v1/cursos/                               | GET    | Público       | Listar cursos                  |
| /api/v1/cursos/?categoria={categoriaId}       | GET    | Público       | Listar cursos por categoria    |
| /api/v1/cursos/?subcategoria={subcategoriaId} | GET    | Público       | Listar cursos por subcategoria |
| /api/v1/cursos/{categoriaId}                  | GET    | Público       | Obtener curso por id           |
| /api/v1/cursos/{categoriaId}                  | PUT    | Privado/Admin | Editar curso                   |
| /api/v1/cursos/{categoriaId}                  | DELETE | Privado/Admin | Eliminar curso                 |

## Crear Curso

```bash
[POST] https://localhost:8080/api/v1/cursos
```

```json
{
  "nombre": "Lógica de programación: Primeros pasos",
  "subcategoria_id": 2
}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"id": 3,
	"nombre": "Lógica de programación: Primeros pasos",
	"categoria": {
		"id": 1,
		"nombre": "Programación"
	},
	"subcategoria": {
		"id": 2,
		"nombre": "Lógica de Programación"
	}
}
```

## </details>

## Listar Cursos

###

```bash
[GET] https://localhost:8080/api/v1/cursos
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
	{
	"content": [
		{
			"id": 2,
			"nombre": "Subcategoría 1",
			"categoria": {
				"id": 1,
				"nombre": "Categoría 2"
			}
		}
	],
	"pageable": {
		"sort": {
			"sorted": false,
			"unsorted": true,
			"empty": true
		},
		"pageNumber": 0,
		"pageSize": 25,
		"offset": 0,
		"paged": true,
		"unpaged": false
	},
	"last": true,
	"totalPages": 1,
	"totalElements": 1,
	"sort": {
		"sorted": false,
		"unsorted": true,
		"empty": true
	},
	"numberOfElements": 1,
	"first": true,
	"number": 0,
	"size": 25,
	"empty": false
}
```

## Listar Cursos por Categoría

###

```bash
[GET] https://localhost:8080/api/v1/cursos?categoria={categoriaId}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"content": [
		{
			"id": 3,
			"nombre": "Todos los cursos"
		},
		{
			"id": 4,
			"nombre": "C#: conociendo el lenguaje"
		},
		{
			"id": 5,
			"nombre": "C#: introducción a la Orientación de Objetos"
		},
		{
			"id": 6,
			"nombre": "C#: usando herencia e implementando interfaces"
		}
	],
	"pageable": {
		"sort": {
			"empty": true,
			"sorted": false,
			"unsorted": true
		},
		"offset": 0,
		"pageNumber": 0,
		"pageSize": 25,
		"paged": true,
		"unpaged": false
	},
	"last": true,
	"totalElements": 4,
	"totalPages": 1,
	"number": 0,
	"size": 25,
	"sort": {
		"empty": true,
		"sorted": false,
		"unsorted": true
	},
	"first": true,
	"numberOfElements": 1,
	"empty": false
}
```

## </details>

## Listar Cursos por Subcategoría

###

```bash
[GET] https://localhost:8080/api/v1/cursos?categoria={categoriaId}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"content": [
		{
			"id": 3,
			"nombre": "Todos los cursos"
		},
		{
			"id": 4,
			"nombre": "C#: conociendo el lenguaje"
		},
		{
			"id": 5,
			"nombre": "C#: introducción a la Orientación de Objetos"
		},
		{
			"id": 6,
			"nombre": "C#: usando herencia e implementando interfaces"
		}
	],
	"pageable": {
		"sort": {
			"empty": true,
			"sorted": false,
			"unsorted": true
		},
		"offset": 0,
		"pageNumber": 0,
		"pageSize": 25,
		"paged": true,
		"unpaged": false
	},
	"last": true,
	"totalElements": 4,
	"totalPages": 1,
	"number": 0,
	"size": 25,
	"sort": {
		"empty": true,
		"sorted": false,
		"unsorted": true
	},
	"first": true,
	"numberOfElements": 1,
	"empty": false
}
```

## </details>

## Obtener Curso por Id

###

```bash
[GET] https://localhost:8080/api/v1/cursos/{cursoId}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"id": 3,
	"nombre": "Curso",
	"categoria": {
		"id": 1,
		"nombre": "Categoría"
	},
	"subcategoria": {
		"id": 2,
		"nombre": "Subcategoría"
	}
}
```

## </details>

## Editar Curso

###

```bash
[PUT] https://localhost:8080/api/v1/cursos/{cursoId}
```

```json
{
  "nombre": "Curso Modificado",
  "subcategoria_id": 2
}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"id": 3,
	"nombre": "Curso Modificado",
	"categoria": {
		"id": 1,
		"nombre": "Categoría"
	},
	"subcategoria": {
		"id": 2,
		"nombre": "Subcategoría"
	}
}
```

## </details>

## Eliminar Curso

###

```bash
[DELETE] https://localhost:8080/api/v1/cursos/{cursoId}
```

<details><summary><b>Output</b></summary>
<br/>

> Respuesta sin cuerpo

## </details>

---

## Publicaciones

#### Endpoints para Publicaciones

---

| Enpoint                               | Método                                               | Acceso        | Descripción          |
| ------------------------------------- | ---------------------------------------------------- | ------------- | -------------------- | ------------------------------------- | --- |
| /api/v1/publicaciones/                | POST                                                 | Privado/Admin | Crear curso          |
| /api/v1/publicaciones/                | GET                                                  | Público       | Listar publicaciones |
| <!--                                  | /api/v1/publicaciones/?categoria={categoriaId}       | GET           | Público              | Listar publicaciones por categoria    |
| <!--                                  | /api/v1/publicaciones/?subcategoria={subcategoriaId} | GET           | Público              | Listar publicaciones por subcategoria | --> |
| /api/v1/publicaciones/{publicacionId} | GET                                                  | Público       | Obtener curso por id |
| /api/v1/publicaciones/{publicacionId} | PUT                                                  | Privado/Admin | Editar curso         |
| /api/v1/publicaciones/{publicacionId} | DELETE                                               | Privado/Admin | Eliminar curso       |

## Crear Publicacion

```bash
[POST] https://localhost:8080/api/v1/publicaciones
```

```json
{
  "nombre": "Lógica de programación: Primeros pasos",
  "subcategoria_id": 2
}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"id": 3,
	"nombre": "Lógica de programación: Primeros pasos",
	"categoria": {
		"id": 1,
		"nombre": "Programación"
	},
	"subcategoria": {
		"id": 2,
		"nombre": "Lógica de Programación"
	}
}
```

## </details>

## Listar Publicaciones

###

```bash
[GET] https://localhost:8080/api/v1/cursos
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
	{
	"content": [
		{
			"id": 2,
			"nombre": "Subcategoría 1",
			"categoria": {
				"id": 1,
				"nombre": "Categoría 2"
			}
		}
	],
	"pageable": {
		"sort": {
			"sorted": false,
			"unsorted": true,
			"empty": true
		},
		"pageNumber": 0,
		"pageSize": 25,
		"offset": 0,
		"paged": true,
		"unpaged": false
	},
	"last": true,
	"totalPages": 1,
	"totalElements": 1,
	"sort": {
		"sorted": false,
		"unsorted": true,
		"empty": true
	},
	"numberOfElements": 1,
	"first": true,
	"number": 0,
	"size": 25,
	"empty": false
}
```

## Obtener Publicación por Id

###

```bash
[GET] https://localhost:8080/api/v1/publicaciones/{publicacionId}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"id": 3,
	"nombre": "Curso",
	"categoria": {
		"id": 1,
		"nombre": "Categoría"
	},
	"subcategoria": {
		"id": 2,
		"nombre": "Subcategoría"
	}
}
```

## </details>

## Editar Curso

###

```bash
[PUT] https://localhost:8080/api/v1/cursos/{cursoId}
```

```json
{
  "nombre": "Curso Modificado",
  "subcategoria_id": 2
}
```

<details><summary><b>Output</b></summary>
<br/>

```javascript
{
	"id": 3,
	"nombre": "Curso Modificado",
	"categoria": {
		"id": 1,
		"nombre": "Categoría"
	},
	"subcategoria": {
		"id": 2,
		"nombre": "Subcategoría"
	}
}
```

## </details>

## Eliminar Curso

###

```bash
[DELETE] https://localhost:8080/api/v1/cursos/{cursoId}
```

<details><summary><b>Output</b></summary>
<br/>

> Respuesta sin cuerpo

## </details>

## Insignia por completar el challenge

![Insignia](insignia.png)
