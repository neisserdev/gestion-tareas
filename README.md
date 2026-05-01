# 🗂️ Sistema de Gestión de Tareas

API REST desarrollada con **Spring Boot 4** (v4.0.6) para la gestión de tareas con PostgreSQL, validación, paginación y documentación OpenAPI.

## 🛠️ Tecnologías

- **Java 17** + **Spring Boot 4**
- **Spring Data JPA** - Persistencia de datos
- **PostgreSQL** - Base de datos
- **Spring Validation** - Validación de requests
- **Spring Cache + Caffeine** - Cacheo de respuestas GET
- **Springdoc OpenAPI (Swagger UI)** - Documentación de la API
- **Spring Boot Actuator** - Endpoints de monitoreo
- **MapStruct** - Mapeo Entity ⇄ DTO
- **Maven** - Gestión de dependencias
- **Lombok** - Reducción de código
- **Docker** - Containerización

## 📋 Requisitos

- Java 17+
- Maven 3.6+
- PostgreSQL 12+

## 🗄️ Configuración de Base de Datos Local

### PostgreSQL

**1. Instalar y configurar PostgreSQL:**
```bash
# Conectar a PostgreSQL
sudo -u postgres psql

# En PostgreSQL:
CREATE DATABASE gestion_tareas;
CREATE USER gestion_user WITH PASSWORD 'gestion123';
GRANT ALL PRIVILEGES ON DATABASE gestion_tareas TO gestion_user;
```

**2. Configurar [src/main/resources/application.properties](src/main/resources/application.properties):**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gestion_tareas
spring.datasource.username=gestion_user
spring.datasource.password=gestion123
spring.jpa.hibernate.ddl-auto=update
```

Nota: en este repo el ejemplo por defecto usa `dbtareas` y credenciales de desarrollo. Ajusta estos valores a tu entorno.

## 📚 Documentación (Swagger / OpenAPI)

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## 🔌 Endpoints de la API

Base URL: `http://localhost:8080/api/tareas`

### Listar tareas (paginado)

`GET /api/tareas`

Parámetros de paginación (Spring `Pageable`):
- `page` (0-based, default 0)
- `size` (default 20)
- `sort` (ej: `sort=fechaCreacion,desc`)

Respuesta (`200 OK`) envuelta en `PaginaResponseDTO`:
```json
{
	"datos": [
		{
			"id": 1,
			"titulo": "Comprar pan",
			"descripcion": "En la tarde",
			"fechaCreacion": "2026-04-12",
			"fechaActualizacion": "2026-04-12",
			"completada": false
		}
	],
	"pagina": 0,
	"tamano": 20,
	"totalElementos": 1,
	"totalPaginas": 1
}
```

### Obtener tarea por ID

`GET /api/tareas/{id}` → `200 OK`

Si no existe → `404 Not Found` (ver formato de error abajo).

### Crear tarea

`POST /api/tareas` → `201 Created`

Body (`TareaRequestDTO`):
```json
{
	"titulo": "Comprar pan",
	"descripcion": "En la tarde"
}
```

Validaciones:
- `titulo`: obligatorio, máximo 100 caracteres
- `descripcion`: opcional, máximo 255 caracteres

### Actualizar tarea

`PUT /api/tareas/{id}` → `200 OK`

Body: igual a creación.

### Marcar como completada

`PATCH /api/tareas/{id}` → `200 OK`

Marca `completada=true` y devuelve la tarea actualizada.

### Eliminar tarea

`DELETE /api/tareas/{id}` → `204 No Content`

## ⚠️ Formato de errores

La API expone errores con `ProblemDetail`.

### Validación

En errores de validación, la API sigue el estándar **RFC 9457 (Problem Details for HTTP APIs)**: define el “contrato” (estructura) de cómo debe verse un error HTTP en formato JSON.

En Spring Boot, esto se materializa con **`ProblemDetail`**, que es la clase utilizada para representar y devolver esos errores de forma consistente en toda la API.

`400 Bad Request` (ejemplo):
```json
{
	"type": "about:blank",
	"title": "Error de validación",
	"status": 400,
	"detail": "La solicitud contiene campos inválidos. Revise el detalle de errores.",
	"instance": "/api/tareas",
	"timestamp": "2026-04-12T00:00:00Z",
	"errors": {
		"titulo": "El título es obligatorio"
	},
	"errorCount": 1
}
```

### Recurso no encontrado

`404 Not Found` (ejemplo):
```json
{
	"type": "about:blank",
	"title": "Tarea no encontrada",
	"status": 404,
	"detail": "Tarea no encontrada con ID: 123",
	"instance": "/api/tareas/123",
	"timestamp": "2026-04-12T00:00:00Z"
}
```


## 🚀 Ejecutar la Aplicación

```bash
# Ejecutar (Linux/macOS)
./mvnw spring-boot:run

# O en Windows
mvnw.cmd spring-boot:run
```

## 📝 Notas

- Las tablas se crean automáticamente con `hibernate.ddl-auto=update`
- La paginación usa `page` (base 0), `size` y `sort`
- Los `GET` están cacheados (Caffeine) según la configuración de `spring.cache.*`

## 👨‍💻 Autor

**Neisser Torres Peña** - [@neisserdev](https://github.com/neisserdev)
