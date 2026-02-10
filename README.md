# DevConnect Backend API

Sistema backend para una plataforma de conexión entre clientes y desarrolladores freelance. Permite gestionar perfiles de programadores, sus proyectos, disponibilidad horaria y solicitudes de servicio.

---

## Descripción Técnica

### Stack Tecnológico

| Componente | Tecnología | Versión |
|------------|------------|---------|
| **Framework** | Spring Boot | 4.0.2 |
| **Lenguaje** | Java | 21 |
| **Base de Datos** | PostgreSQL | 12+ |
| **Autenticación** | JWT (JSON Web Tokens) | 0.12.3 |
| **ORM** | Hibernate/JPA | 7.2.1 |
| **Seguridad** | Spring Security | 7.0.3 |
| **Email** | Spring Mail | - |
| **Build** | Gradle (Kotlin DSL) | 9.3 |
| **Contenedorización** | Docker | - |

### Arquitectura del Sistema

```
┌─────────────────────────────────────────────────────────────────────┐
│                         CLIENTE (Frontend)                          │
│                    Angular / React / Mobile App                     │
└─────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼ HTTP/HTTPS (REST API)
┌─────────────────────────────────────────────────────────────────────┐
│                      SPRING BOOT APPLICATION                        │
├─────────────────────────────────────────────────────────────────────┤
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐              │
│  │   Security   │  │     CORS     │  │ JWT Filter   │              │
│  │    Config    │──│   Handler    │──│  Validator   │              │
│  └──────────────┘  └──────────────┘  └──────────────┘              │
├─────────────────────────────────────────────────────────────────────┤
│                        CONTROLLERS (REST)                           │
│  ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌────────────┐       │
│  │    Auth    │ │  Profiles  │ │  Projects  │ │Applications│       │
│  └────────────┘ └────────────┘ └────────────┘ └────────────┘       │
│  ┌────────────┐ ┌────────────┐ ┌────────────┐                      │
│  │Technologies│ │Availability│ │    Mail    │                      │
│  └────────────┘ └────────────┘ └────────────┘                      │
├─────────────────────────────────────────────────────────────────────┤
│                          SERVICES                                   │
│         (Lógica de negocio, validaciones, transacciones)           │
├─────────────────────────────────────────────────────────────────────┤
│                        REPOSITORIES                                 │
│              (Acceso a datos con Spring Data JPA)                  │
├─────────────────────────────────────────────────────────────────────┤
│                          ENTITIES                                   │
│    UserAuth │ UserProfile │ Project │ Technology │ Application     │
└─────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────┐
│                         POSTGRESQL DATABASE                         │
└─────────────────────────────────────────────────────────────────────┘
```

### Modelo de Datos (Diagrama ER)

```
┌──────────────────┐       ┌──────────────────┐
│    user_auth     │       │   user_profile   │
├──────────────────┤       ├──────────────────┤
│ id (PK)          │       │ id (PK)          │
│ email            │  1:1  │ photo_url        │
│ name             │◄─────►│ phone_number     │
│ password         │       │ title            │
│ rol              │       │ bio              │
│ profile_id (FK)  │       │ experience_years │
└──────────────────┘       └──────────────────┘
                                    │
                           ┌────────┴────────┐
                           │                 │
                           ▼ 1:N             ▼ N:M
              ┌──────────────────┐   ┌──────────────────┐
              │     projects     │   │   user_skills    │
              ├──────────────────┤   ├──────────────────┤
              │ id (PK)          │   │ user_id (FK)     │
              │ project          │   │ skill_id (FK)    │
              │ description      │   └──────────────────┘
              │ project_url      │           │
              │ image_url        │           ▼
              │ user_id (FK)     │   ┌──────────────────┐
              └──────────────────┘   │   technologies   │
                       │             ├──────────────────┤
                       │ N:M         │ id (PK)          │
                       ▼             │ technology       │
              ┌──────────────────┐   └──────────────────┘
              │project_technologies│
              ├──────────────────┤
              │ project_id (FK)  │
              │ technology_id(FK)│
              └──────────────────┘

┌──────────────────────────────────────────────────────────────────┐
│                    service_applications                          │
├──────────────────────────────────────────────────────────────────┤
│ id (PK)           │ client_id (FK)     │ programmer_id (FK)      │
│ client_name       │ programmer_name    │ status                  │
│ subject           │ description        │ budget                  │
│ scheduled_date    │ duration_minutes   │ start_time / end_time   │
│ meeting_link      │ rejection_reason   │ created_at / updated_at │
└──────────────────────────────────────────────────────────────────┘
```

### Sistema de Roles

| Rol | Descripción | Permisos |
|-----|-------------|----------|
| **STANDARD** | Usuario cliente | Solicitar servicios, ver perfiles públicos |
| **PROGRAMMER** | Desarrollador freelance | Gestionar perfil, proyectos, disponibilidad, responder solicitudes |
| **ADMIN** | Administrador | Acceso completo al sistema |

---

## Flujo de Funcionamiento

### 1. Flujo de Registro y Autenticación

```
┌─────────┐                                    ┌─────────┐
│ Cliente │                                    │ Backend │
└────┬────┘                                    └────┬────┘
     │                                              │
     │ POST /api/auth/register                      │
     │ {email, password, name}                      │
     ├─────────────────────────────────────────────►│
     │                                              │ ─┐ Validar email único
     │                                              │  │ Encriptar password
     │                                              │  │ Crear UserAuth + Profile
     │                                              │  │ Generar JWT
     │                                              │ ◄┘
     │ {token, id, name, email, rol}               │
     │◄─────────────────────────────────────────────┤
     │                                              │
     │ Guardar token en localStorage                │
     │                                              │
```

### 2. Flujo de Solicitud de Servicio

```
┌─────────┐                    ┌─────────┐                    ┌────────────┐
│ Cliente │                    │ Backend │                    │Programador │
└────┬────┘                    └────┬────┘                    └─────┬──────┘
     │                              │                               │
     │ 1. GET /api/profiles/developers                             │
     ├─────────────────────────────►│                               │
     │ [lista de programadores]     │                               │
     │◄─────────────────────────────┤                               │
     │                              │                               │
     │ 2. GET /api/availability/{programmerId}                      │
     ├─────────────────────────────►│                               │
     │ {disponibilidad horaria}     │                               │
     │◄─────────────────────────────┤                               │
     │                              │                               │
     │ 3. POST /api/applications/client/{clientId}                  │
     │ {programmerId, subject, description, scheduledDate...}       │
     ├─────────────────────────────►│                               │
     │                              │ ─┐ Crear solicitud            │
     │                              │  │ Estado: PENDING            │
     │                              │ ◄┘                            │
     │ {applicationId, status...}   │                               │
     │◄─────────────────────────────┤                               │
     │                              │                               │
     │                              │ 4. Notificación email         │
     │                              ├──────────────────────────────►│
     │                              │                               │
     │                              │ 5. GET /api/applications/programmer/{id}
     │                              │◄──────────────────────────────┤
     │                              │ [solicitudes pendientes]      │
     │                              ├──────────────────────────────►│
     │                              │                               │
     │                              │ 6. PATCH /api/applications/{id}/status
     │                              │ {status: "ACCEPTED", meetingLink}
     │                              │◄──────────────────────────────┤
     │                              │ ─┐ Actualizar estado          │
     │                              │ ◄┘                            │
     │                              │                               │
     │ 7. Notificación aceptación   │                               │
     │◄─────────────────────────────┤                               │
     │                              │                               │
```

### 3. Estados de una Solicitud

```
                    ┌─────────┐
                    │ PENDING │ (Estado inicial)
                    └────┬────┘
                         │
           ┌─────────────┼─────────────┐
           ▼             ▼             ▼
    ┌──────────┐  ┌──────────┐  ┌───────────┐
    │ ACCEPTED │  │ REJECTED │  │ CANCELLED │
    └────┬─────┘  └──────────┘  └───────────┘
         │         (Final)        (Cliente)
         ▼
    ┌───────────┐
    │ COMPLETED │
    └───────────┘
       (Final)
```

---

## Documentación de Endpoints REST

### Base URL
```
Desarrollo: http://localhost:8080
Producción: https://proyect-backend-dgcy.onrender.com
```

### Autenticación
Todos los endpoints protegidos requieren el header:
```
Authorization: Bearer {JWT_TOKEN}
```

---

### 🔓 Endpoints Públicos (Sin Autenticación)

#### Auth - Autenticación

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/auth/login` | Iniciar sesión |
| `POST` | `/api/auth/register` | Registrar usuario estándar |
| `POST` | `/api/auth/register-programmer` | Registrar programador |
| `POST` | `/api/auth/register-admin` | Registrar administrador |
| `GET` | `/api/auth/users/{id}/exists` | Verificar si usuario existe |

##### POST /api/auth/login
```json
// Request
{
  "email": "usuario@email.com",
  "password": "contraseña123"
}

// Response (200 OK)
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "id": 1,
  "name": "Juan Pérez",
  "email": "usuario@email.com",
  "rol": "STANDARD"
}
```

##### POST /api/auth/register
```json
// Request
{
  "email": "nuevo@email.com",
  "password": "contraseña123",
  "name": "Nuevo Usuario"
}

// Response (201 Created)
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "id": 2,
  "name": "Nuevo Usuario",
  "email": "nuevo@email.com",
  "rol": "STANDARD"
}
```

##### POST /api/auth/register-programmer
```json
// Request
{
  "email": "dev@email.com",
  "password": "contraseña123",
  "name": "Dev Senior",
  "speciality": "Backend Java"
}

// Response (201 Created)
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "id": 3,
  "name": "Dev Senior",
  "email": "dev@email.com",
  "rol": "PROGRAMMER"
}
```

---

#### Profiles - Perfiles (GET Públicos)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/profiles/{id}` | Obtener perfil por ID |
| `GET` | `/api/profiles/{id}/exists` | Verificar si perfil existe |
| `GET` | `/api/profiles/standard/{userId}` | Obtener perfil estándar |
| `GET` | `/api/profiles/developer/{userId}` | Obtener perfil de desarrollador |
| `GET` | `/api/profiles/developers` | Listar todos los desarrolladores |

##### GET /api/profiles/developers
```json
// Response (200 OK)
[
  {
    "id": 3,
    "name": "Dev Senior",
    "email": "dev@email.com",
    "photoUrl": "https://example.com/photo.jpg",
    "title": "Senior Backend Developer",
    "bio": "5 años de experiencia en Java y Spring",
    "skills": ["Java", "Spring Boot", "PostgreSQL"],
    "experienceYears": 5
  }
]
```

---

#### Projects - Proyectos (GET Públicos)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/projects` | Listar todos los proyectos |
| `GET` | `/api/projects/{id}` | Obtener proyecto por ID |
| `GET` | `/api/projects/user/{userId}` | Proyectos de un usuario |

##### GET /api/projects
```json
// Response (200 OK)
[
  {
    "id": 1,
    "project": "Sistema E-commerce",
    "description": "Plataforma de comercio electrónico completa",
    "projectUrl": "https://github.com/user/ecommerce",
    "imageUrl": "https://example.com/project.jpg",
    "ownerId": 3,
    "technologies": ["Java", "Spring Boot", "React"]
  }
]
```

---

#### Technologies - Tecnologías (GET Públicos)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/technologies` | Listar todas las tecnologías |
| `GET` | `/api/technologies/{id}` | Obtener tecnología por ID |
| `GET` | `/api/technologies/by-name/{name}` | Buscar tecnología por nombre |

---

### 🔐 Endpoints Protegidos (Requieren JWT)

#### Profiles - Gestión de Perfiles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/profiles/standard?userId={id}` | Crear perfil estándar |
| `PUT` | `/api/profiles/standard/{userId}` | Actualizar perfil estándar |
| `PATCH` | `/api/profiles/standard?userId={id}` | Actualización parcial |
| `DELETE` | `/api/profiles/standard?userId={id}` | Eliminar perfil estándar |
| `POST` | `/api/profiles/developer?userId={id}` | Crear perfil desarrollador |
| `PUT` | `/api/profiles/developer?userId={id}` | Actualizar perfil desarrollador |
| `PATCH` | `/api/profiles/developer?userId={id}` | Actualización parcial |
| `DELETE` | `/api/profiles/developer?userId={id}` | Eliminar perfil desarrollador |

##### POST /api/profiles/developer?userId=3
```json
// Request
{
  "photoUrl": "https://example.com/photo.jpg",
  "title": "Full Stack Developer",
  "bio": "Apasionado por crear soluciones innovadoras",
  "skills": [1, 2, 3],  // IDs de tecnologías
  "experienceYears": 5
}

// Response (201 Created)
{
  "id": 3,
  "name": "Dev Senior",
  "email": "dev@email.com",
  "photoUrl": "https://example.com/photo.jpg",
  "title": "Full Stack Developer",
  "bio": "Apasionado por crear soluciones innovadoras",
  "skills": ["Java", "Spring Boot", "React"],
  "experienceYears": 5
}
```

---

#### Projects - Gestión de Proyectos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/projects?userId={id}` | Crear proyecto |
| `PUT` | `/api/projects/{id}` | Actualizar proyecto completo |
| `PATCH` | `/api/projects/{id}` | Actualización parcial |
| `DELETE` | `/api/projects/{id}` | Eliminar proyecto |

##### POST /api/projects?userId=3
```json
// Request
{
  "project": "API REST Microservicios",
  "description": "Arquitectura de microservicios con Spring Cloud",
  "projectUrl": "https://github.com/user/microservices",
  "imageUrl": "https://example.com/project.png",
  "technologies": [1, 2, 4]  // IDs de tecnologías
}

// Response (201 Created)
{
  "id": 5,
  "project": "API REST Microservicios",
  "description": "Arquitectura de microservicios con Spring Cloud",
  "projectUrl": "https://github.com/user/microservices",
  "imageUrl": "https://example.com/project.png",
  "ownerId": 3,
  "technologies": ["Java", "Spring Boot", "Docker"]
}
```

---

#### Technologies - Gestión de Tecnologías

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/technologies` | Crear tecnología |
| `PUT` | `/api/technologies/{id}` | Actualizar tecnología |

##### POST /api/technologies
```json
// Request
{
  "technology": "Kubernetes"
}

// Response (201 Created)
{
  "id": 10,
  "technology": "Kubernetes"
}
```

---

#### Availability - Disponibilidad Horaria

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/availability/{userId}` | Crear/actualizar disponibilidad |
| `GET` | `/api/availability/{userId}` | Obtener disponibilidad |
| `DELETE` | `/api/availability/{userId}` | Eliminar disponibilidad |

##### POST /api/availability/3
```json
// Request
{
  "days": [
    {
      "dayOfWeek": "MONDAY",
      "timeSlots": [
        {"startTime": "09:00", "endTime": "12:00"},
        {"startTime": "14:00", "endTime": "18:00"}
      ]
    },
    {
      "dayOfWeek": "TUESDAY",
      "timeSlots": [
        {"startTime": "10:00", "endTime": "17:00"}
      ]
    }
  ]
}

// Response (201 Created)
{
  "userId": 3,
  "days": [...]
}
```

---

#### Applications - Solicitudes de Servicio

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/applications/client/{clientId}` | Crear solicitud |
| `GET` | `/api/applications/{id}` | Obtener solicitud por ID |
| `GET` | `/api/applications/client/{clientId}` | Solicitudes del cliente |
| `GET` | `/api/applications/client/{clientId}?status=PENDING` | Filtrar por estado |
| `GET` | `/api/applications/programmer/{programmerId}` | Solicitudes del programador |
| `GET` | `/api/applications/programmer/{programmerId}?status=ACCEPTED` | Filtrar por estado |
| `PATCH` | `/api/applications/{id}/status` | Cambiar estado |
| `DELETE` | `/api/applications/{id}` | Eliminar solicitud |

##### POST /api/applications/client/1
```json
// Request
{
  "programmerId": 3,
  "subject": "Desarrollo de API REST",
  "description": "Necesito desarrollar una API para mi aplicación móvil",
  "budget": "$500-1000",
  "scheduledDate": 1707580800000,  // Timestamp en ms
  "durationMinutes": 60,
  "startTime": 1707584400000,
  "endTime": 1707588000000
}

// Response (201 Created)
{
  "id": 1,
  "clientId": 1,
  "clientName": "Juan Cliente",
  "programmerId": 3,
  "programmerName": "Dev Senior",
  "status": "PENDING",
  "subject": "Desarrollo de API REST",
  "description": "Necesito desarrollar una API para mi aplicación móvil",
  "budget": "$500-1000",
  "scheduledDate": 1707580800000,
  "durationMinutes": 60,
  "startTime": 1707584400000,
  "endTime": 1707588000000,
  "createdAt": 1707500000000,
  "updatedAt": 1707500000000,
  "meetingLink": null,
  "rejectionReason": null
}
```

##### PATCH /api/applications/1/status (Aceptar)
```json
// Request
{
  "status": "ACCEPTED",
  "meetingLink": "https://meet.google.com/abc-defg-hij"
}

// Response (200 OK)
{
  "id": 1,
  "status": "ACCEPTED",
  "meetingLink": "https://meet.google.com/abc-defg-hij",
  ...
}
```

##### PATCH /api/applications/1/status (Rechazar)
```json
// Request
{
  "status": "REJECTED",
  "rejectionReason": "No tengo disponibilidad en las fechas solicitadas"
}

// Response (200 OK)
{
  "id": 1,
  "status": "REJECTED",
  "rejectionReason": "No tengo disponibilidad en las fechas solicitadas",
  ...
}
```

---

#### Mail - Notificaciones por Email

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/mail/send` | Enviar email genérico |
| `POST` | `/api/mail/notify/new-application` | Notificar nueva solicitud |
| `POST` | `/api/mail/notify/application-accepted` | Notificar aceptación |
| `POST` | `/api/mail/notify/application-rejected` | Notificar rechazo |

---

## Guía de Usuario

### Para Administradores

#### 1. Configuración Inicial

1. **Crear primer admin** (requiere acceso directo a BD o endpoint público inicial):
   ```bash
   POST /api/auth/register-admin
   {
     "email": "admin@sistema.com",
     "password": "adminSecure123!",
     "name": "Administrador Principal"
   }
   ```

2. **Configurar tecnologías base**:
   ```bash
   POST /api/technologies
   Authorization: Bearer {admin_token}
   
   # Crear tecnologías comunes
   {"technology": "Java"}
   {"technology": "Spring Boot"}
   {"technology": "JavaScript"}
   {"technology": "React"}
   {"technology": "Angular"}
   {"technology": "PostgreSQL"}
   {"technology": "Docker"}
   ```

#### 2. Gestión del Sistema

- **Monitorear salud del sistema**: `GET /actuator/health`
- **Ver métricas**: `GET /actuator/metrics`

#### 3. Gestión de Usuarios

Los administradores pueden:
- Crear otros administradores
- Acceder a todos los perfiles
- Gestionar tecnologías del catálogo

---

### Para Programadores

#### 1. Registro y Configuración de Perfil

```bash
# 1. Registrarse como programador
POST /api/auth/register-programmer
{
  "email": "dev@email.com",
  "password": "securePass123",
  "name": "María Developer",
  "speciality": "Full Stack"
}

# 2. Guardar el token recibido

# 3. Completar perfil de desarrollador
POST /api/profiles/developer?userId={tu_id}
Authorization: Bearer {token}
{
  "photoUrl": "https://tu-foto.com/avatar.jpg",
  "title": "Senior Full Stack Developer",
  "bio": "5 años creando aplicaciones web escalables",
  "skills": [1, 2, 3, 4],  // IDs de tecnologías
  "experienceYears": 5
}
```

#### 2. Agregar Proyectos al Portafolio

```bash
POST /api/projects?userId={tu_id}
Authorization: Bearer {token}
{
  "project": "E-commerce Platform",
  "description": "Plataforma completa con carrito, pagos y gestión",
  "projectUrl": "https://github.com/tuuser/ecommerce",
  "imageUrl": "https://example.com/screenshot.png",
  "technologies": [1, 2, 5]
}
```

#### 3. Configurar Disponibilidad

```bash
POST /api/availability/{tu_id}
Authorization: Bearer {token}
{
  "days": [
    {
      "dayOfWeek": "MONDAY",
      "timeSlots": [
        {"startTime": "09:00", "endTime": "12:00"},
        {"startTime": "14:00", "endTime": "18:00"}
      ]
    },
    {
      "dayOfWeek": "WEDNESDAY",
      "timeSlots": [
        {"startTime": "10:00", "endTime": "16:00"}
      ]
    },
    {
      "dayOfWeek": "FRIDAY",
      "timeSlots": [
        {"startTime": "09:00", "endTime": "13:00"}
      ]
    }
  ]
}
```

#### 4. Gestionar Solicitudes

```bash
# Ver solicitudes pendientes
GET /api/applications/programmer/{tu_id}?status=PENDING
Authorization: Bearer {token}

# Aceptar solicitud
PATCH /api/applications/{applicationId}/status
Authorization: Bearer {token}
{
  "status": "ACCEPTED",
  "meetingLink": "https://meet.google.com/xxx-yyyy-zzz"
}

# Rechazar solicitud
PATCH /api/applications/{applicationId}/status
Authorization: Bearer {token}
{
  "status": "REJECTED",
  "rejectionReason": "No disponible en esas fechas"
}

# Marcar como completada
PATCH /api/applications/{applicationId}/status
Authorization: Bearer {token}
{
  "status": "COMPLETED"
}
```

---

### Para Clientes (Usuarios Standard)

#### 1. Registro

```bash
POST /api/auth/register
{
  "email": "cliente@email.com",
  "password": "miPassword123",
  "name": "Juan Cliente"
}
```

#### 2. Buscar Programadores

```bash
# Ver todos los desarrolladores disponibles
GET /api/profiles/developers

# Ver proyectos de un desarrollador
GET /api/projects/user/{programmerId}

# Ver disponibilidad del desarrollador
GET /api/availability/{programmerId}
```

#### 3. Solicitar Servicio

```bash
POST /api/applications/client/{tu_clientId}
Authorization: Bearer {token}
{
  "programmerId": 3,
  "subject": "Desarrollo de App Móvil",
  "description": "Necesito una app para gestión de inventario",
  "budget": "$2000-3000",
  "scheduledDate": 1707580800000,
  "durationMinutes": 60,
  "startTime": 1707584400000,
  "endTime": 1707588000000
}
```

#### 4. Seguimiento de Solicitudes

```bash
# Ver todas mis solicitudes
GET /api/applications/client/{tu_clientId}
Authorization: Bearer {token}

# Filtrar por estado
GET /api/applications/client/{tu_clientId}?status=ACCEPTED

# Cancelar solicitud (solo si está PENDING)
PATCH /api/applications/{applicationId}/status
{
  "status": "CANCELLED"
}
```

---

## Instalación y Despliegue

### Desarrollo Local

```bash
# 1. Clonar repositorio
git clone <repository-url>
cd proyect_backend

# 2. Configurar PostgreSQL local
# Crear base de datos: proyect_backend

# 3. Configurar variables de entorno (opcional)
# O usar valores por defecto en application.yaml

# 4. Ejecutar
./gradlew bootRun
```

### Variables de Entorno

| Variable | Descripción | Default |
|----------|-------------|---------|
| `PGHOST` | Host PostgreSQL | localhost |
| `PGPORT` | Puerto PostgreSQL | 5432 |
| `PGDATABASE` | Nombre de BD | proyect_backend |
| `PGUSER` | Usuario BD | root |
| `PGPASSWORD` | Contraseña BD | root |
| `PGSSLMODE` | Modo SSL | disable |
| `JWT_SECRET` | Clave secreta JWT | (ver yaml) |
| `SMTP_SERVER_HOST` | Host SMTP | localhost |
| `SMTP_SERVER_PORT` | Puerto SMTP | 587 |
| `SMTP_SERVER_USERNAME` | Usuario SMTP | - |
| `SMTP_SERVER_PASSWORD` | Contraseña SMTP | - |

### Docker

```bash
# Construir y ejecutar
docker-compose up --build

# Ver logs
docker-compose logs -f app
```

### Producción (Render)

El proyecto está configurado para desplegar automáticamente en Render:
- URL: `https://proyect-backend-dgcy.onrender.com`
- BD: Neon PostgreSQL (SSL requerido)

---

## Códigos de Error

| Código | Significado |
|--------|-------------|
| `200` | OK - Operación exitosa |
| `201` | Created - Recurso creado |
| `204` | No Content - Eliminación exitosa |
| `400` | Bad Request - Datos inválidos |
| `401` | Unauthorized - Token inválido/expirado |
| `403` | Forbidden - Sin permisos |
| `404` | Not Found - Recurso no encontrado |
| `409` | Conflict - Email duplicado, etc. |
| `500` | Internal Server Error |

### Formato de Error
```json
{
  "timestamp": "2026-02-10T15:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "El email ya está registrado",
  "path": "/api/auth/register"
}
```

---

## Contacto y Soporte

Para reportar problemas o solicitar nuevas funcionalidades, crear un issue en el repositorio.

---

**Versión**: 0.0.1-SNAPSHOT  
**Última actualización**: Febrero 2026
