package ups.edu.ec.proyect_backend.auth.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ups.edu.ec.proyect_backend.auth.dtos.AdminCreationRequestDto;
import ups.edu.ec.proyect_backend.auth.dtos.AuthResponseDto;
import ups.edu.ec.proyect_backend.auth.dtos.LoginRequestDto;
import ups.edu.ec.proyect_backend.auth.dtos.ProgrammerRegistrationRequestDto;
import ups.edu.ec.proyect_backend.auth.dtos.RegisterRequestDto;
import ups.edu.ec.proyect_backend.auth.dtos.UserExistsResponseDto;
import ups.edu.ec.proyect_backend.auth.services.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Login - Endpoint público
     * POST /auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        AuthResponseDto response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Registro - Endpoint público
     * POST /auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequest) {
        AuthResponseDto response = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Registro de Programador - Endpoint público
     * POST /auth/register-programmer
     */
    @PostMapping("/register-programmer")
    public ResponseEntity<AuthResponseDto> registerProgrammer(@Valid @RequestBody ProgrammerRegistrationRequestDto programmerRequest) {
        AuthResponseDto response = authService.registerProgrammer(programmerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Creación de Admin - Endpoint protegido (requiere autenticación de ADMIN)
     * POST /auth/create-admin
     */
    @PostMapping("/register-admin")
    public ResponseEntity<AuthResponseDto> registerAdmin(@Valid @RequestBody AdminCreationRequestDto adminRequest) {
        AuthResponseDto response = authService.registerAdmin(adminRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Verificar si el usuario existe por ID
     * GET /auth/users/{id}/exists
     */
    @GetMapping("/users/{id}/exists")
    public ResponseEntity<UserExistsResponseDto> checkUserExists(@PathVariable Long id) {
        UserExistsResponseDto response = authService.checkUserExists(id);
        return ResponseEntity.ok(response);
    }
}
