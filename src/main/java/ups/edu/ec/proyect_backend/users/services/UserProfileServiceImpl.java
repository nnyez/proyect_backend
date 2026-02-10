package ups.edu.ec.proyect_backend.users.services;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ups.edu.ec.proyect_backend.auth.entities.UserAuthEntity;
import ups.edu.ec.proyect_backend.auth.models.Rol;
import ups.edu.ec.proyect_backend.auth.repositories.UserAuthRepository;
import ups.edu.ec.proyect_backend.technologies.entities.TechnologyEntity;
import ups.edu.ec.proyect_backend.technologies.repositories.TechnologyRepository;
import ups.edu.ec.proyect_backend.users.dtos.ProfileExistsResponseDto;
import ups.edu.ec.proyect_backend.users.dtos.UserProfileResponseDto;
import ups.edu.ec.proyect_backend.users.dtos.developer.CreateDeveloperProfileDto;
import ups.edu.ec.proyect_backend.users.dtos.developer.DeveloperProfileResponseDto;
import ups.edu.ec.proyect_backend.users.dtos.developer.PatchDeveloperProfileDto;
import ups.edu.ec.proyect_backend.users.dtos.developer.UpdateDeveloperProfileDto;
import ups.edu.ec.proyect_backend.users.dtos.standard.CreateStandardProfileDto;
import ups.edu.ec.proyect_backend.users.dtos.standard.PatchStandardProfileDto;
import ups.edu.ec.proyect_backend.users.dtos.standard.StandardProfileResponseDto;
import ups.edu.ec.proyect_backend.users.dtos.standard.UpdateStandardProfileDto;
import ups.edu.ec.proyect_backend.users.entities.UserProfileEntity;
import ups.edu.ec.proyect_backend.users.mappers.UserProfileMapper;
import ups.edu.ec.proyect_backend.users.repositories.UserProfileRepository;

/**
 * Implementación de servicio para operaciones CRUD de perfiles de usuario
 * Soporta perfiles STANDARD y DEVELOPER/ADMIN
 */
@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private final UserAuthRepository userAuthRepository;
    private final UserProfileRepository userProfileRepository;
    private final TechnologyRepository technologyRepository;

    public UserProfileServiceImpl(UserAuthRepository userAuthRepository,
            UserProfileRepository userProfileRepository,
            TechnologyRepository technologyRepository) {
        this.userAuthRepository = userAuthRepository;
        this.userProfileRepository = userProfileRepository;
        this.technologyRepository = technologyRepository;
    }

    // ============ HELPER METHODS ============

    /**
     * Obtiene el UserAuthEntity por su ID
     * @throws RuntimeException si no existe el usuario
     */
    private UserAuthEntity getUserAuthOrThrow(Long userId) {
        return userAuthRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con ID: " + userId));
    }

    /**
     * Obtiene el UserProfileEntity por el ID del usuario
     * @throws RuntimeException si no existe el perfil
     */
    private UserProfileEntity getUserProfileByUserIdOrThrow(Long userId) {
        UserAuthEntity userAuth = getUserAuthOrThrow(userId);
        return userAuth.getProfile() != null ? userAuth.getProfile()
                : null;
    }

    /**
     * Carga las tecnologías por sus IDs
     */
    private Set<TechnologyEntity> loadTechnologies(Set<Long> skillIds) {
        if (skillIds == null || skillIds.isEmpty()) {
            return Set.of();
        }
        return new java.util.HashSet<>(technologyRepository.findAllById(skillIds));
    }

    // ============ STANDARD PROFILE OPERATIONS ============

    @Override
    public StandardProfileResponseDto createStandardProfile(Long userId,
            CreateStandardProfileDto createDto) {
        UserAuthEntity userAuth = getUserAuthOrThrow(userId);

        UserProfileEntity profile = UserProfileMapper.fromCreateStandardDto(createDto);
        profile = userProfileRepository.save(profile);
        userAuth.setProfile(profile);
        userAuthRepository.save(userAuth);

        return UserProfileMapper.toStandardResponse(profile, userAuth);
    }

    @Override
    @Transactional(readOnly = true)
    public StandardProfileResponseDto getStandardProfile(Long userId) {
        UserAuthEntity userAuth = getUserAuthOrThrow(userId);
        UserProfileEntity profile = userAuth.getProfile();

        if (profile == null) {
            throw new RuntimeException("El usuario no tiene un perfil asociado");
        }

        return UserProfileMapper.toStandardResponse(profile, userAuth);
    }

    @Override
    public StandardProfileResponseDto updateStandardProfile(Long userId,
            UpdateStandardProfileDto updateDto) {
        UserAuthEntity userAuth = getUserAuthOrThrow(userId);
        UserProfileEntity profile = userAuth.getProfile();

        if (profile == null) {
            throw new RuntimeException("El usuario no tiene un perfil asociado");
        }

        profile = UserProfileMapper.fromUpdateStandardDto(updateDto, profile);
        profile = userProfileRepository.save(profile);

        return UserProfileMapper.toStandardResponse(profile, userAuth);
    }

    @Override
    public StandardProfileResponseDto patchStandardProfile(Long userId,
            PatchStandardProfileDto patchDto) {
        UserAuthEntity userAuth = getUserAuthOrThrow(userId);
        UserProfileEntity profile = userAuth.getProfile();

        if (profile == null) {
            throw new RuntimeException("El usuario no tiene un perfil asociado");
        }

        profile = UserProfileMapper.fromPatchStandardDto(patchDto, profile);
        profile = userProfileRepository.save(profile);

        return UserProfileMapper.toStandardResponse(profile, userAuth);
    }

    @Override
    public void deleteStandardProfile(Long userId) {
        UserAuthEntity userAuth = getUserAuthOrThrow(userId);
        UserProfileEntity profile = userAuth.getProfile();

        if (profile == null) {
            throw new RuntimeException("El usuario no tiene un perfil asociado");
        }

        userAuth.setProfile(null);
        userAuthRepository.save(userAuth);
        userProfileRepository.delete(profile);
    }

    // ============ DEVELOPER PROFILE OPERATIONS ============

    @Override
    public DeveloperProfileResponseDto createDeveloperProfile(Long userId,
            CreateDeveloperProfileDto createDto) {
        UserAuthEntity userAuth = getUserAuthOrThrow(userId);

        UserProfileEntity profile = UserProfileMapper.fromCreateDeveloperDto(createDto);

        // Asignar skills si existe
        if (createDto.getSkillIds() != null && !createDto.getSkillIds().isEmpty()) {
            Set<TechnologyEntity> technologies = loadTechnologies(createDto.getSkillIds());
            UserProfileMapper.assignSkills(profile, technologies);
        }

        profile = userProfileRepository.save(profile);
        userAuth.setProfile(profile);
        userAuthRepository.save(userAuth);

        return UserProfileMapper.toDeveloperResponse(profile, userAuth);
    }

    @Override
    @Transactional(readOnly = true)
    public DeveloperProfileResponseDto getDeveloperProfile(Long userId) {
        UserAuthEntity userAuth = getUserAuthOrThrow(userId);
        UserProfileEntity profile = userAuth.getProfile();

        if (profile == null) {
            throw new RuntimeException("El usuario no tiene un perfil asociado");
        }

        return UserProfileMapper.toDeveloperResponse(profile, userAuth);
    }

    @Override
    public DeveloperProfileResponseDto updateDeveloperProfile(Long userId,
            UpdateDeveloperProfileDto updateDto) {
        UserAuthEntity userAuth = getUserAuthOrThrow(userId);
        UserProfileEntity profile = userAuth.getProfile();

        if (profile == null) {
            throw new RuntimeException("El usuario no tiene un perfil asociado");
        }

        profile = UserProfileMapper.fromUpdateDeveloperDto(updateDto, profile);

        // Actualizar skills si existen
        if (updateDto.getSkillIds() != null && !updateDto.getSkillIds().isEmpty()) {
            Set<TechnologyEntity> technologies = loadTechnologies(updateDto.getSkillIds());
            UserProfileMapper.assignSkills(profile, technologies);
        } else {
            profile.setSkills(Set.of());
        }

        profile = userProfileRepository.save(profile);

        return UserProfileMapper.toDeveloperResponse(profile, userAuth);
    }

    @Override
    public DeveloperProfileResponseDto patchDeveloperProfile(Long userId,
            PatchDeveloperProfileDto patchDto) {
        UserAuthEntity userAuth = getUserAuthOrThrow(userId);
        UserProfileEntity profile = userAuth.getProfile();

        if (profile == null) {
            throw new RuntimeException("El usuario no tiene un perfil asociado");
        }

        profile = UserProfileMapper.fromPatchDeveloperDto(patchDto, profile);

        // Actualizar skills solo si se proporcionan en el PATCH
        if (patchDto.getSkillIds() != null && !patchDto.getSkillIds().isEmpty()) {
            Set<TechnologyEntity> technologies = loadTechnologies(patchDto.getSkillIds());
            UserProfileMapper.assignSkills(profile, technologies);
        }

        profile = userProfileRepository.save(profile);

        return UserProfileMapper.toDeveloperResponse(profile, userAuth);
    }

    @Override
    public void deleteDeveloperProfile(Long userId) {
        UserAuthEntity userAuth = getUserAuthOrThrow(userId);
        UserProfileEntity profile = userAuth.getProfile();

        if (profile == null) {
            throw new RuntimeException("El usuario no tiene un perfil asociado");
        }

        userAuth.setProfile(null);
        userAuthRepository.save(userAuth);
        userProfileRepository.delete(profile);
    }

    /**
     * Obtiene el perfil general de un usuario por su ID (profileId)
     * Retorna información completa del perfil incluyendo datos del usuario autenticado
     */
    @Override
    @Transactional(readOnly = true)
    public UserProfileResponseDto getProfileById(Long profileId) {
        UserProfileEntity profile = userProfileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("El perfil con ID " + profileId + " no existe"));

        UserAuthEntity userAuth = profile.getAuth();
        
        // Convertir skills a Set de IDs
        Set<Long> skillIds = new java.util.HashSet<>();
        if (profile.getSkills() != null) {
            skillIds = profile.getSkills().stream()
                    .map(TechnologyEntity::getId)
                    .collect(java.util.stream.Collectors.toSet());
        }

        // Crear y retornar el DTO
        return new UserProfileResponseDto(
                profile.getId(),
                profile.getPhotoUrl(),
                profile.getPhoneNumber(),
                profile.getTitle(),
                profile.getBio(),
                skillIds,
                profile.getExperienceYears(),
                userAuth != null ? userAuth.getName() : "",
                userAuth != null ? userAuth.getEmail() : "");
    }

    /**
     * Verifica si existe un perfil con el ID proporcionado
     */
    @Override
    @Transactional(readOnly = true)
    public ProfileExistsResponseDto checkProfileExists(Long profileId) {
        boolean exists = userProfileRepository.existsById(profileId);
        return new ProfileExistsResponseDto(exists, exists ? profileId : null);
    }

    /**
     * Obtiene todos los perfiles de desarrolladores/programadores
     */
    @Override
    @Transactional(readOnly = true)
    public List<DeveloperProfileResponseDto> getAllDeveloperProfiles() {
        // Obtener todos los usuarios con rol PROGRAMMER o ADMIN
        List<UserAuthEntity> developers = userAuthRepository.findAll().stream()
                .filter(user -> user.getRol() == Rol.PROGRAMMER || user.getRol() == Rol.ADMIN)
                .filter(user -> user.getProfile() != null)
                .collect(Collectors.toList());

       List<DeveloperProfileResponseDto> res=  developers.stream()
                .map(userAuth -> {
                    UserProfileEntity profile = userAuth.getProfile();
                    return UserProfileMapper.toDeveloperResponse(profile, userAuth);
                })
                .collect(Collectors.toList());

        return res;
    }
}
