# Proyect Backend API

Una API RESTful moderna desarrollada con **Spring Boot 4.0.2** y **Java 21** para gestionar usuarios, perfiles, proyectos, tecnologías y solicitudes de servicio. El sistema incluye autenticación segura con **JWT (JSON Web Tokens)** y soporte para tres roles de usuario: STANDARD, PROGRAMMER y ADMIN.

---

## 📋 Características Principales

- ✅ **Autenticación segura** con JWT y encriptación de contraseñas
- ✅ **Gestión de tres roles** (STANDARD, PROGRAMMER, ADMIN)
- ✅ **Perfiles de usuario personalizados** (Estándar y Desarrollador)
- ✅ **Gestión de proyectos** con múltiples tecnologías
- ✅ **Solicitudes de servicio** entre clientes y programadores
- ✅ **Base de datos PostgreSQL** con validación de integridad
- ✅ **API RESTful** con documentación de endpoints
- ✅ **Manejo robusto de errores** y validaciones
- ✅ **Soporte para envío de emails** (SMTP)
- ✅ **Docker y Docker Compose** para despliegue

---

## 🛠️ Tecnologías

- **Spring Boot**: 4.0.2
- **Java**: 21
- **Base de Datos**: PostgreSQL
- **Autenticación**: JWT (JSON Web Tokens)
- **ORM**: Hibernate/JPA
- **Validación**: Jakarta Validation
- **Email**: Spring Mail (SMTP)
- **Build**: Gradle 8.x
- **Contenedorización**: Docker & Docker Compose

---

## 📦 Requisitos Previos

- **Java 21** o superior
- **PostgreSQL 12** o superior
- **Docker** y **Docker Compose** (opcional, para despliegue)
- **Gradle 8.x** (incluido con gradlew)

---

## 🚀 Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone <repository-url>
cd proyect_backend
```

### 2. Configurar variables de entorno

Crear un archivo `.env` en la raíz del proyecto con las siguientes variables:

```env
# PostgreSQL Configuration
PGHOST=localhost
PGPORT=5432
PGDATABASE=proyect_backend
PGUSER=root
PGPASSWORD=root
PGSSLMODE=disable

# JWT Configuration
JWT_SECRET=mySecretKeyForJWTTokenGenerationAndValidationThatNeedsToBeVeryLongAndSecureForProductionUse1234567890!@#$%^&*()_+{}[]|:;<>,.?/

# SMTP Configuration (Opcional)
SMTP_SERVER_HOST=localhost
SMTP_SERVER_PORT=587
SMTP_SERVER_USERNAME=your-email@example.com
SMTP_SERVER_PASSWORD=your-password
SITE_MAIL_RECIEVER=admin@example.com
```

### 3. Opción A: Ejecutar con Gradle (Desarrollo Local)

```bash
# Limpiar y compilar
./gradlew clean build -x test

# Ejecutar la aplicación
./gradlew bootRun
```

La aplicación estará disponible en `http://localhost:8080`

### 3. Opción B: Ejecutar con Docker Compose (Recomendado)

```bash
# Construir e iniciar los servicios
docker-compose up --build

# En otra terminal, ver logs
docker-compose logs -f proyect_backend
```

---

## 📚 Documentación de Endpoints

### 🔐 Autenticación (`/api/auth`)

#### 1. **Registro de Usuario Estándar**
```http
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "name": "Juan Usuario"
}
```
**Respuesta (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "id": 1,
  "name": "Juan Usuario",
  "email": "user@example.com",
  "rol": "STANDARD"
}
```

#### 2. **Registro de Programador**
```http
POST /api/auth/register-programmer
Content-Type: application/json

{
  "email": "programmer@example.com",
  "password": "password123",
  "name": "Pedro Programador",
  "speciality": "Backend Java"
}
```
**Respuesta (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "id": 2,
  "name": "Pedro Programador",
  "email": "programmer@example.com",
  "rol": "PROGRAMMER"
}
```

#### 3. **Crear Administrador** (Requiere autenticación)
```http
POST /api/auth/register-admin
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "email": "admin@example.com",
  "password": "admin123",
  "name": "Carlos Admin"
}
```
**Respuesta (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "id": 3,
  "name": "Carlos Admin",
  "email": "admin@example.com",
  "rol": "ADMIN"
}
```

#### 4. **Login**
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```
**Respuesta (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "id": 1,
  "name": "Juan Usuario",
  "email": "user@example.com",
  "rol": "STANDARD"
}
```

#### 5. **Verificar si Usuario Existe**
```http
GET /api/auth/users/{id}/exists
Authorization: Bearer {JWT_TOKEN}
```
**Respuesta (200 OK):**
```json
{
  "exists": true
}
```

---

### 👤 Perfiles de Usuario (`/api/profiles`)

#### Obtener Perfil General
```http
GET /api/profiles/{userId}
Authorization: Bearer {JWT_TOKEN}
```

#### Verificar si Perfil Existe
```http
GET /api/profiles/{userId}/exists
Authorization: Bearer {JWT_TOKEN}
```

#### 📋 Perfil Estándar

**Crear Perfil Estándar**
```http
POST /api/profiles/standard?userId=1
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "photoUrl": "https://example.com/photo.jpg",
  "phoneNumber": "+1234567890"
}
```

**Obtener Perfil Estándar**
```http
GET /api/profiles/standard/{userId}
Authorization: Bearer {JWT_TOKEN}
```

**Actualizar Perfil Estándar (PUT - Completo)**
```http
PUT /api/profiles/standard/{userId}
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "photoUrl": "https://example.com/new-photo.jpg",
  "phoneNumber": "+1987654321"
}
```

**Actualizar Perfil Estándar (PATCH - Parcial)**
```http
PATCH /api/profiles/standard?userId=1
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "phoneNumber": "+1987654321"
}
```

**Eliminar Perfil Estándar**
```http
DELETE /api/profiles/standard?userId=1
Authorization: Bearer {JWT_TOKEN}
```

#### 💻 Perfil Desarrollador/Admin

**Crear Perfil Desarrollador**
```http
POST /api/profiles/developer?userId=2
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "photoUrl": "https://example.com/dev-photo.jpg",
  "skills": ["Java", "Spring Boot", "PostgreSQL"],
  "yearsOfExperience": 5,
  "title": "Senior Backend Developer",
  "biography": "Experiencia en desarrollo de APIs RESTful..."
}
```

**Obtener Perfil Desarrollador**
```http
GET /api/profiles/developer/{userId}
Authorization: Bearer {JWT_TOKEN}
```

**Actualizar Perfil Desarrollador (PUT - Completo)**
```http
PUT /api/profiles/developer?userId=2
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "photoUrl": "https://example.com/new-dev-photo.jpg",
  "skills": ["Java", "Spring Boot", "PostgreSQL", "Docker"],
  "yearsOfExperience": 6,
  "title": "Lead Backend Developer",
  "biography": "Experiencia en desarrollo de APIs RESTful y arquitectura de microservicios..."
}
```

**Actualizar Perfil Desarrollador (PATCH - Parcial)**
```http
PATCH /api/profiles/developer?userId=2
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "yearsOfExperience": 6,
  "title": "Lead Backend Developer"
}
```

**Obtener Todos los Desarrolladores**
```http
GET /api/profiles/developers
Authorization: Bearer {JWT_TOKEN}
```

**Eliminar Perfil Desarrollador**
```http
DELETE /api/profiles/developer?userId=2
Authorization: Bearer {JWT_TOKEN}
```

---

### 🏗️ Proyectos (`/api/projects`)

**Crear Proyecto**
```http
POST /api/projects?userId=2
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "title": "Sistema de Gestión",
  "description": "Plataforma completa de gestión...",
  "startDate": "2024-01-15",
  "endDate": "2024-06-30",
  "technologies": [1, 2, 3]
}
```

**Obtener Proyecto por ID**
```http
GET /api/projects/{projectId}
Authorization: Bearer {JWT_TOKEN}
```

**Obtener Todos los Proyectos**
```http
GET /api/projects
Authorization: Bearer {JWT_TOKEN}
```

**Obtener Proyectos de un Usuario**
```http
GET /api/projects/user/{userId}
Authorization: Bearer {JWT_TOKEN}
```

**Actualizar Proyecto Completamente (PUT)**
```http
PUT /api/projects/{projectId}
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "title": "Sistema de Gestión Actualizado",
  "description": "Descripción actualizada...",
  "startDate": "2024-01-15",
  "endDate": "2024-12-31",
  "technologies": [1, 2, 3, 4]
}
```

**Actualizar Proyecto Parcialmente (PATCH)**
```http
PATCH /api/projects/{projectId}
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "endDate": "2024-12-31",
  "technologies": [1, 2, 3, 4]
}
```

**Eliminar Proyecto**
```http
DELETE /api/projects/{projectId}
Authorization: Bearer {JWT_TOKEN}
```

---

### 🔧 Tecnologías (`/api/technologies`)

**Crear Tecnología**
```http
POST /api/technologies
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "name": "Spring Boot",
  "description": "Framework Java para desarrollo de aplicaciones",
  "version": "4.0.2"
}
```

**Obtener Tecnología por ID**
```http
GET /api/technologies/{id}
Authorization: Bearer {JWT_TOKEN}
```

**Obtener Todas las Tecnologías**
```http
GET /api/technologies
Authorization: Bearer {JWT_TOKEN}
```

**Obtener Tecnología por Nombre**
```http
GET /api/technologies/by-name/{name}
Authorization: Bearer {JWT_TOKEN}
```

**Actualizar Tecnología (PUT)**
```http
PUT /api/technologies/{id}
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "name": "Spring Boot",
  "description": "Framework Java actualizado",
  "version": "4.1.0"
}
```

---

### 📝 Solicitudes de Servicio (`/api/applications`)

**Crear Solicitud de Servicio**
```http
POST /api/applications/client/{clientId}
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "programmerId": 2,
  "title": "Desarrollo de API REST",
  "description": "Necesito desarrollar una API REST para...",
  "budget": 1000.00,
  "dueDate": "2024-03-30"
}
```

**Obtener Solicitud por ID**
```http
GET /api/applications/{id}
Authorization: Bearer {JWT_TOKEN}
```

**Obtener Solicitudes del Cliente**
```http
GET /api/applications/client/{clientId}
Authorization: Bearer {JWT_TOKEN}

# Con filtro de estado (PENDING, ACCEPTED, REJECTED, COMPLETED, CANCELLED)
GET /api/applications/client/{clientId}?status=PENDING
Authorization: Bearer {JWT_TOKEN}
```

**Obtener Solicitudes del Programador**
```http
GET /api/applications/programmer/{programmerId}
Authorization: Bearer {JWT_TOKEN}

# Con filtro de estado
GET /api/applications/programmer/{programmerId}?status=ACCEPTED
Authorization: Bearer {JWT_TOKEN}
```

**Actualizar Estado de la Solicitud**
```http
PATCH /api/applications/{id}/status
Content-Type: application/json
Authorization: Bearer {JWT_TOKEN}

{
  "status": "ACCEPTED"  # PENDING, ACCEPTED, REJECTED, COMPLETED, CANCELLED
}
```

**Eliminar Solicitud**
```http
DELETE /api/applications/{id}
Authorization: Bearer {JWT_TOKEN}
```

---

## 🔐 Seguridad y Autenticación

### JWT (JSON Web Tokens)

Todos los endpoints (excepto login y registro) requieren un JWT válido en el header `Authorization`:

```
Authorization: Bearer {JWT_TOKEN}
```

### Configuración de JWT

```yaml
jwt:
  secret: ${JWT_SECRET}           # Clave secreta para firmar tokens
  expiration: 86400000            # 24 horas en milisegundos
  refresh-expiration: 604800000   # 7 días en milisegundos
  issuer: proyect_backend-api
  header: Authorization
  prefix: "Bearer "
```

### Roles del Sistema

| Rol | Descripción | Permisos |
|-----|-------------|----------|
| **STANDARD** | Usuario estándar del sistema | Crear perfil estándar, solicitar servicios |
| **PROGRAMMER** | Desarrollador/Programador | Crear perfil desarrollador, proyectos, aceptar solicitudes |
| **ADMIN** | Administrador del sistema | Acceso completo a todas las funcionalidades |

---

## 📊 Estructura del Proyecto

```
src/
├── main/
│   ├── java/ups/edu/ec/proyect_backend/
│   │   ├── ProyectBackendApplication.java      # Clase principal
│   │   ├── auth/                               # Módulo de autenticación
│   │   │   ├── controllers/
│   │   │   ├── services/
│   │   │   ├── repositories/
│   │   │   ├── entities/
│   │   │   └── dtos/
│   │   ├── users/                              # Módulo de perfiles de usuario
│   │   │   ├── controllers/
│   │   │   ├── services/
│   │   │   ├── repositories/
│   │   │   ├── entities/
│   │   │   └── dtos/
│   │   ├── projects/                           # Módulo de proyectos
│   │   │   ├── controllers/
│   │   │   ├── services/
│   │   │   ├── repositories/
│   │   │   ├── entities/
│   │   │   └── dtos/
│   │   ├── technologies/                       # Módulo de tecnologías
│   │   │   ├── controllers/
│   │   │   ├── services/
│   │   │   ├── repositories/
│   │   │   ├── entities/
│   │   │   └── dtos/
│   │   ├── applications/                       # Módulo de solicitudes
│   │   │   ├── controllers/
│   │   │   ├── services/
│   │   │   ├── repositories/
│   │   │   ├── entities/
│   │   │   └── dtos/
│   │   ├── mail/                              # Módulo de email
│   │   │   ├── controllers/
│   │   │   ├── services/
│   │   │   └── dtos/
│   │   ├── core/                              # Componentes centrales
│   │   │   ├── exceptions/
│   │   │   └── entities/
│   │   └── availability/                      # Módulo de disponibilidad
│   │       └── ...
│   └── resources/
│       ├── application.yaml                   # Configuración principal
│       ├── static/                            # Archivos estáticos
│       └── templates/                         # Plantillas
└── test/
    └── java/...                               # Tests unitarios
```

---

## 🐳 Docker y Docker Compose

### Archivo docker-compose.yml

El proyecto incluye un `docker-compose.yml` que define dos servicios:

1. **PostgreSQL**: Base de datos
2. **Proyect Backend**: Aplicación Spring Boot

### Comandos útiles

```bash
# Iniciar los servicios
docker-compose up --build

# Ver logs en tiempo real
docker-compose logs -f proyect_backend

# Detener los servicios
docker-compose down

# Eliminar volúmenes (cuidado: borra datos)
docker-compose down -v

# Ver estado de los servicios
docker-compose ps
```

---

## 📡 Variables de Entorno

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `PGHOST` | Host de PostgreSQL | localhost |
| `PGPORT` | Puerto de PostgreSQL | 5432 |
| `PGDATABASE` | Nombre de la base de datos | proyect_backend |
| `PGUSER` | Usuario de PostgreSQL | root |
| `PGPASSWORD` | Contraseña de PostgreSQL | root |
| `PGSSLMODE` | Modo SSL para PostgreSQL | disable |
| `JWT_SECRET` | Clave secreta para JWT | (Ver application.yaml) |
| `SMTP_SERVER_HOST` | Host SMTP | localhost |
| `SMTP_SERVER_PORT` | Puerto SMTP | 587 |
| `SMTP_SERVER_USERNAME` | Usuario SMTP | (vacío) |
| `SMTP_SERVER_PASSWORD` | Contraseña SMTP | (vacío) |
| `SITE_MAIL_RECIEVER` | Email receptor de notificaciones | (vacío) |

---

## 🧪 Testing

```bash
# Ejecutar todos los tests
./gradlew test

# Ejecutar tests de un módulo específico
./gradlew test --tests "*AuthServiceTest*"

# Generar reporte de cobertura
./gradlew test jacocoTestReport
```

---

## 📱 Flujo de Uso Típico

### 1. **Registro e Inicio de Sesión**
```
1. Usuario realiza POST /auth/register (obtiene token)
2. O usuario realiza POST /auth/login
3. Token JWT se usa en todos los endpoints posteriores
```

### 2. **Creación de Perfil**
```
1. Usuario autenticado realiza POST /api/profiles/standard o /developer
2. Completa su perfil con información personal/profesional
```

### 3. **Gestión de Proyectos**
```
1. Programador crea proyectos con POST /api/projects
2. Asocia tecnologías a los proyectos
3. Actualiza proyectos según sea necesario
```

### 4. **Solicitudes de Servicio**
```
1. Cliente crea solicitud con POST /api/applications/client/{clientId}
2. Programador recibe solicitud y la ve en GET /api/applications/programmer/{programmerId}
3. Programador cambia estado con PATCH /api/applications/{id}/status
4. Cliente puede monitorear estado de sus solicitudes
```

---

## 🚨 Manejo de Errores

La API devuelve respuestas de error en formato JSON:

```json
{
  "timestamp": "2024-02-10T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "El email ya está registrado",
  "path": "/api/auth/register"
}
```

### Códigos HTTP Utilizados

| Código | Significado |
|--------|------------|
| `200 OK` | Solicitud exitosa |
| `201 Created` | Recurso creado exitosamente |
| `204 No Content` | Solicitud exitosa sin contenido |
| `400 Bad Request` | Solicitud inválida o validación fallida |
| `401 Unauthorized` | Autenticación requerida |
| `403 Forbidden` | Acceso prohibido |
| `404 Not Found` | Recurso no encontrado |
| `409 Conflict` | Conflicto (ej. email duplicado) |
| `500 Internal Server Error` | Error interno del servidor |

---

## 🔧 Desarrollo y Contribución

### Configurar el entorno de desarrollo

```bash
# Instalar dependencias
./gradlew clean build -x test

# Ejecutar en modo desarrollo
./gradlew bootRun

# Generar JAR ejecutable
./gradlew build
```

### Estructura de commits

```
feat: Agregar nueva funcionalidad
fix: Corregir bug
docs: Actualizar documentación
test: Agregar o modificar tests
refactor: Cambios de refactorización
```

---

## 📝 Notas de Producción

Para desplegar en producción:

1. ✅ Cambiar `JWT_SECRET` a una clave más segura
2. ✅ Configurar base de datos PostgreSQL en servidor remoto
3. ✅ Habilitar HTTPS en el servidor
4. ✅ Configurar SMTP con credenciales reales
5. ✅ Establecer `spring.jpa.hibernate.ddl-auto: validate`
6. ✅ Implementar logging centralizado
7. ✅ Agregar autenticación de dos factores (2FA)
8. ✅ Configurar rate limiting
9. ✅ Usar variables de entorno para todas las credenciales
10. ✅ Implementar CORS apropiadamente

---

## 📞 Soporte y Contacto

Para reportar bugs o sugerencias:

1. Revisar la documentación existente
2. Crear un issue describiendo el problema
3. Incluir pasos para reproducir
4. Adjuntar logs o screenshots si es relevante

---

## 📄 Licencia

Este proyecto es propiedad de [Tu Institución/Empresa]. Todos los derechos reservados.

---

**Última actualización**: 10 de febrero de 2026

---

## 🎯 Roadmap Futuro

- [ ] Integración con pasarelas de pago
- [ ] Sistema de notificaciones en tiempo real (WebSockets)
- [ ] Panel de administración
- [ ] Sistema de calificaciones y reseñas
- [ ] API de reportes avanzados
- [ ] Internacionalización (i18n)
- [ ] Testing automatizado mejorado
- [ ] Documentación con Swagger/OpenAPI
