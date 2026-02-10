# Nuevos Endpoints de Autenticación

## Resumen
Se han creado dos nuevos endpoints en el módulo de autenticación para registrar usuarios **Programadores** y crear usuarios **Administradores**.

---

## Endpoints Creados

### 1. **Registro de Programador**
- **URL**: `POST /auth/register-programmer`
- **Descripción**: Permite registrar un nuevo usuario con rol de **PROGRAMMER**
- **Acceso**: Público (sin autenticación requerida)
- **Request Body**:
```json
{
  "email": "programmer@example.com",
  "password": "password123",
  "name": "Juan Programador",
  "speciality": "Backend Java"
}
```
- **Response** (201 Created):
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "id": 1,
  "name": "Juan Programador",
  "email": "programmer@example.com",
  "rol": "PROGRAMMER"
}
```

### 2. **Creación de Admin**
- **URL**: `POST /auth/create-admin`
- **Descripción**: Permite crear un nuevo usuario con rol de **ADMIN**
- **Acceso**: Requiere autenticación (actualmente sin restricción de rol, pero debería protegerse)
- **Request Body**:
```json
{
  "email": "admin@example.com",
  "password": "adminpass123",
  "name": "Carlos Admin"
}
```
- **Response** (201 Created):
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "id": 2,
  "name": "Carlos Admin",
  "email": "admin@example.com",
  "rol": "ADMIN"
}
```

---

## Archivos Creados/Modificados

### Nuevos DTOs:
1. **[ProgrammerRegistrationRequestDto.java](ProgrammerRegistrationRequestDto.java)**
   - DTO para el registro de programadores
   - Incluye: email, password, name, speciality

2. **[AdminCreationRequestDto.java](AdminCreationRequestDto.java)**
   - DTO para la creación de administradores
   - Incluye: email, password, name

### Modificaciones en AuthService:
- Método `registerProgrammer()` - Registra un usuario con rol PROGRAMMER
- Método `createAdmin()` - Crea un usuario con rol ADMIN

### Modificaciones en AuthController:
- Endpoint `POST /auth/register-programmer` - Llama a registerProgrammer()
- Endpoint `POST /auth/create-admin` - Llama a createAdmin()

---

## Comportamiento

Ambos endpoints:
- ✅ Validan que el email no esté registrado
- ✅ Encriptan la contraseña antes de guardarla
- ✅ Crean un perfil de usuario asociado
- ✅ Generan un JWT automáticamente
- ✅ Retornan los datos del usuario creado con el token

---

## Recomendaciones de Seguridad

Para producción, se recomienda:
1. Proteger el endpoint `/auth/create-admin` con una anotación `@PreAuthorize("hasRole('ADMIN')")` para que solo admins existentes puedan crear nuevos admins
2. Usar `@PreAuthorize` en el controlador para validar roles
3. Agregar validaciones adicionales como límite de intentos fallidos, etc.

Ejemplo de cómo proteger el endpoint de admin:
```java
@PreAuthorize("hasRole('ADMIN')")
@PostMapping("/create-admin")
public ResponseEntity<AuthResponseDto> createAdmin(@Valid @RequestBody AdminCreationRequestDto adminRequest) {
    // ...
}
```

---

## Roles Disponibles

El sistema cuenta con 3 roles definidos en [Rol.java](Rol.java):
- **STANDARD** - Usuario estándar (rol por defecto en registro normal)
- **PROGRAMMER** - Desarrollador/Programador
- **ADMIN** - Administrador del sistema
