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
  - [Tabla Usuario](#tabla-rol)
- [Etiquetas](#etiquetas)
  - [Tabla Etiqueta](#tabla-etiqueta)
- [Categorías](#categorías)
- [Subcategorías](#subcategorias)
- [Cursos](#subcategorias)
- [Publicaciones](#brands)
- [Respuestas](#products)

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

---

## Subcategorías

#### Endpoints para Subcategorías

---

---

## Cursos

#### Endpoints para Cursos

---

## Publicaciones

#### Endpoints para Publicaciones

---
