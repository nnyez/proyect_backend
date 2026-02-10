package ups.edu.ec.proyect_backend.auth.services;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ups.edu.ec.proyect_backend.auth.dtos.AdminCreationRequestDto;
import ups.edu.ec.proyect_backend.auth.dtos.AuthResponseDto;
import ups.edu.ec.proyect_backend.auth.dtos.UserExistsResponseDto;
import ups.edu.ec.proyect_backend.auth.dtos.LoginRequestDto;
import ups.edu.ec.proyect_backend.auth.dtos.ProgrammerRegistrationRequestDto;
import ups.edu.ec.proyect_backend.auth.dtos.RegisterRequestDto;
import ups.edu.ec.proyect_backend.auth.entities.UserAuthEntity;
import ups.edu.ec.proyect_backend.auth.models.Rol;
import ups.edu.ec.proyect_backend.auth.repositories.UserAuthRepository;
import ups.edu.ec.proyect_backend.auth.utils.JwtUtil;
import ups.edu.ec.proyect_backend.users.entities.UserProfileEntity;
import ups.edu.ec.proyect_backend.users.repositories.UserProfileRepository;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserAuthRepository userAuthRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager,
            UserAuthRepository userAuthRepository,
            UserProfileRepository userProfileRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userAuthRepository = userAuthRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Login: Valida credenciales y retorna JWT
     */
    @Transactional(readOnly = true)
    public AuthResponseDto login(LoginRequestDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtil.generateToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String rol = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .findFirst()
                    .orElse("ROLE_STANDAR");

            return new AuthResponseDto(
                    jwt,
                    userDetails.getId(),
                    userDetails.getName(),
                    userDetails.getEmail(),
                    rol);

        } catch (Exception e) {
            throw new RuntimeException("Error en el login: " + e.getMessage());
        }
    }

    /**
     * Registro: Crea nuevo usuario y su perfil, retorna JWT automáticamente
     */
    @Transactional
    public AuthResponseDto register(RegisterRequestDto registerRequest) {
        // 1. Validar que email no exista
        if (userAuthRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // 2. Crear entidad de autenticación
        UserAuthEntity userAuth = new UserAuthEntity();
        userAuth.setEmail(registerRequest.getEmail());
        userAuth.setName(registerRequest.getName()); // El nombre inicial es el email
        userAuth.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userAuth.setRol(Rol.STANDARD); // Rol por defecto

        // 3. Crear perfil de usuario
        UserProfileEntity profile = new UserProfileEntity();
        UserProfileEntity savedProfile = userProfileRepository.save(profile);
        userAuth.setProfile(savedProfile);

        // 4. Guardar en BD
        userAuth = userAuthRepository.save(userAuth);

        // 5. Generar JWT automáticamente
        UserDetailsImpl userDetails = UserDetailsImpl.build(userAuth);
        String jwt = jwtUtil.generateTokenFromUserDetails(userDetails);

        String rol = userAuth.getRol().name();

        // 6. Retornar JWT + datos del usuario registrado
        return new AuthResponseDto(
                jwt,
                userAuth.getId(),
                userAuth.getName(),
                userAuth.getEmail(),
                rol);
    }

    /**
     * Registro de Programador: Crea nuevo usuario con rol PROGRAMMER
     */
    @Transactional
    public AuthResponseDto registerProgrammer(ProgrammerRegistrationRequestDto programmerRequest) {
        // 1. Validar que email no exista
        if (userAuthRepository.existsByEmail(programmerRequest.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // 2. Crear entidad de autenticación
        UserAuthEntity userAuth = new UserAuthEntity();
        userAuth.setEmail(programmerRequest.getEmail());
        userAuth.setName(programmerRequest.getName());
        userAuth.setPassword(passwordEncoder.encode(programmerRequest.getPassword()));
        userAuth.setRol(Rol.PROGRAMMER); // Rol PROGRAMMER

        // 3. Crear perfil de usuario
        UserProfileEntity profile = new UserProfileEntity();
        // Aquí puedes agregar la especialidad al perfil si deseas
        UserProfileEntity savedProfile = userProfileRepository.save(profile);
        userAuth.setProfile(savedProfile);

        // 4. Guardar en BD
        userAuth = userAuthRepository.save(userAuth);

        // 5. Generar JWT automáticamente
        UserDetailsImpl userDetails = UserDetailsImpl.build(userAuth);
        String jwt = jwtUtil.generateTokenFromUserDetails(userDetails);

        String rol = userAuth.getRol().name();

        // 6. Retornar JWT + datos del usuario registrado
        return new AuthResponseDto(
                jwt,
                userAuth.getId(),
                userAuth.getName(),
                userAuth.getEmail(),
                rol);
    }

    /**
     * Creación de Admin: Crea nuevo usuario con rol ADMIN
     * Requiere estar autenticado como ADMIN
     */
    @Transactional
    public AuthResponseDto registerAdmin(AdminCreationRequestDto adminRequest) {
        // 1. Validar que email no exista
        if (userAuthRepository.existsByEmail(adminRequest.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // 2. Crear entidad de autenticación
        UserAuthEntity userAuth = new UserAuthEntity();
        userAuth.setEmail(adminRequest.getEmail());
        userAuth.setName(adminRequest.getName());
        userAuth.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
        userAuth.setRol(Rol.ADMIN); // Rol ADMIN

        // 3. Crear perfil de usuario
        UserProfileEntity profile = new UserProfileEntity();
        UserProfileEntity savedProfile = userProfileRepository.save(profile);
        userAuth.setProfile(savedProfile);

        // 4. Guardar en BD
        userAuth = userAuthRepository.save(userAuth);

        // 5. Generar JWT automáticamente
        UserDetailsImpl userDetails = UserDetailsImpl.build(userAuth);
        String jwt = jwtUtil.generateTokenFromUserDetails(userDetails);

        String rol = userAuth.getRol().name();

        // 6. Retornar JWT + datos del usuario creado
        return new AuthResponseDto(
                jwt,
                userAuth.getId(),
                userAuth.getName(),
                userAuth.getEmail(),
                rol);
    }

    /**
     * Verifica si existe un usuario por ID
     */
    @Transactional(readOnly = true)
    public UserExistsResponseDto checkUserExists(Long userId) {
        boolean exists = userAuthRepository.existsById(userId);
        return new UserExistsResponseDto(exists, userId);
    }
}
