# API REST Tienda - Spring Boot con JWT

Una API REST para una tienda de productos, desarrollada en Spring Boot 
con autenticación JWT, autorización por roles, validaciones y tests unitarios.

# Tecnologías Utilizadas

- Java 21
- Spring Boot 3.5.13
- Spring Security con JWT
- PostgreSQL
- JPA/Hibernate
- Lombok
- JUnit 5 + Mockito
- Maven

# Características Implementadas

## Autenticación y Autorización
- Registro de usuarios con validaciones
- Login con JWT (tokens de 24 horas)
- Autorización por roles (ADMIN, USER)
- Endpoints protegidos con `@PreAuthorize`

## API REST - CRUD Productos
- GET /productos (con paginación)
- GET /productos/{id}
- POST /productos (solo ADMIN)
- PUT /productos/{id} (solo ADMIN)
- DELETE /productos/{id} (solo ADMIN)

## Arquitectura y Buenas Prácticas
- Arquitectura en capas (Controller → Service → Repository)
- DTOs para separar modelo interno de exposición
- Validaciones automáticas con `@Valid`
- Manejo global de excepciones con `@ControllerAdvice`
- Paginación de resultados
- Contraseñas encriptadas con BCrypt

## Testing
- Tests unitarios con Mockito
- Mocking de dependencias
- Prueba de casos exitosos y de error

# Estructura del Proyecto
src/
├── main/
│   ├── java/com/avalos/tienda/
│   │   ├── controller/       # Endpoints HTTP
│   │   ├── service/          # Lógica de negocio
│   │   ├── repository/       # Acceso a datos
│   │   ├── entity/           # Modelos JPA
│   │   ├── dto/              # Data Transfer Objects
│   │   ├── mapper/           # Conversión DTO ↔ Entity
│   │   ├── security/         # JWT y autenticación
│   │   ├── exception/        # Excepciones personalizadas
│   │   └── config/           # Configuración Spring
│   └── resources/
│       └── application.properties
└── test/
└── java/com/avalos/tienda/
└── service/          # Tests unitarios

# Configuración

## 1. Base de Datos
Crea una BD PostgreSQL:
```sql
CREATE DATABASE tienda;
```
## 2. application.properties
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tienda
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

jwt.secret=MyVerySecureSecretKeyForJWTSigningThatIsLongEnough123456789!@#$%^&*
jwt.expiration=86400000
```
## 3. Dependencias
```bash
mvn clean install
```
# Ejecutar la Aplicación

```bash
mvn spring-boot:run
```
La API estará en `http://localhost:8080`

# Endpoints Principales

## Autenticación (públicos)
POST /auth/register    - Registrar usuario
POST /auth/login       - Login y obtener JWT
## Productos (requieren JWT)
GET    /productos                 - Listar todos (USER)
GET    /productos/{id}            - Obtener uno (USER)
POST   /productos                 - Crear (ADMIN)
PUT    /productos/{id}            - Editar (ADMIN)
DELETE /productos/{id}            - Eliminar (ADMIN)

## Ejemplo: Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@test.com","password":"password123"}'
```
Respuesta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "mensaje": "Login exitoso",
  "usuario": {
    "id": 1,
    "email": "user@test.com",
    "nombre": "Test User",
    "rol": "USER"
  }
}
```
## Usar el JWT
```bash
curl -X GET http://localhost:8080/productos \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```
## Tests Unitarios
```bash
mvn test
```
Casos cubiertos:
- Login exitoso
- Login con contraseña incorrecta
- Validaciones de entrada

# Lo que aprendí

- Arquitectura en capas en Spring Boot
- Autenticación y autorización con JWT
- DTOs y validaciones
- Manejo global de excepciones
- Testing unitario con Mockito
- Integración con PostgreSQL

# Próximos pasos

- Tests de integración
- Documentación con Swagger
- Caché de datos
- Rate limiting
---