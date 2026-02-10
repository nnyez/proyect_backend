package ups.edu.ec.proyect_backend.users.services;

import java.util.List;

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

/**
 * Interfaz de servicio para operaciones CRUD de perfiles de usuario
 * Soporta perfiles STANDARD y DEVELOPER/ADMIN
 */
public interface UserProfileService {

    /**
     * Obtiene el perfil general de un usuario por su ID
     * @param profileId ID del perfil
     * @return Respuesta del perfil con información general
     */
    UserProfileResponseDto getProfileById(Long profileId);

    // ============ STANDARD PROFILE OPERATIONS ============

    /**
     * Crea un perfil STANDARD para un usuario
     * @param userId ID del usuario autenticado
     * @param createDto DTO con datos del perfil
     * @return Respuesta del perfil creado
     */
    StandardProfileResponseDto createStandardProfile(Long userId, CreateStandardProfileDto createDto);

    /**
     * Obtiene el perfil STANDARD de un usuario
     * @param userId ID del usuario
     * @return Respuesta del perfil
     */
    StandardProfileResponseDto getStandardProfile(Long userId);

    /**
     * Actualiza completamente (PUT) un perfil STANDARD
     * @param userId ID del usuario
     * @param updateDto DTO con nuevos datos
     * @return Respuesta del perfil actualizado
     */
    StandardProfileResponseDto updateStandardProfile(Long userId, UpdateStandardProfileDto updateDto);

    /**
     * Actualiza parcialmente (PATCH) un perfil STANDARD
     * @param userId ID del usuario
     * @param patchDto DTO con datos a actualizar
     * @return Respuesta del perfil actualizado
     */
    StandardProfileResponseDto patchStandardProfile(Long userId, PatchStandardProfileDto patchDto);

    /**
     * Elimina el perfil STANDARD de un usuario
     * @param userId ID del usuario
     */
    void deleteStandardProfile(Long userId);

    // ============ DEVELOPER PROFILE OPERATIONS ============

    /**
     * Crea un perfil DEVELOPER para un usuario
     * @param userId ID del usuario autenticado
     * @param createDto DTO con datos del perfil
     * @return Respuesta del perfil creado
     */
    DeveloperProfileResponseDto createDeveloperProfile(Long userId, CreateDeveloperProfileDto createDto);

    /**
     * Obtiene el perfil DEVELOPER de un usuario
     * @param userId ID del usuario
     * @return Respuesta del perfil
     */
    DeveloperProfileResponseDto getDeveloperProfile(Long userId);

    /**
     * Actualiza completamente (PUT) un perfil DEVELOPER
     * @param userId ID del usuario
     * @param updateDto DTO con nuevos datos
     * @return Respuesta del perfil actualizado
     */
    DeveloperProfileResponseDto updateDeveloperProfile(Long userId, UpdateDeveloperProfileDto updateDto);

    /**
     * Actualiza parcialmente (PATCH) un perfil DEVELOPER
     * @param userId ID del usuario
     * @param patchDto DTO con datos a actualizar
     * @return Respuesta del perfil actualizado
     */
    DeveloperProfileResponseDto patchDeveloperProfile(Long userId, PatchDeveloperProfileDto patchDto);

    /**
     * Elimina el perfil DEVELOPER de un usuario
     * @param userId ID del usuario
     */
    void deleteDeveloperProfile(Long userId);

    /**
     * Verifica si existe un perfil con el ID proporcionado
     * @param profileId ID del perfil a verificar
     * @return ProfileExistsResponseDto con el resultado de la búsqueda
     */
    ProfileExistsResponseDto checkProfileExists(Long profileId);

    /**
     * Obtiene todos los perfiles de desarrolladores/programadores
     * @return Lista de perfiles de desarrolladores
     */
    List<DeveloperProfileResponseDto> getAllDeveloperProfiles();

    /**
     * Obtiene todos los perfiles de usuarios independiente del rol
     * @return Lista de todos los perfiles de usuarios
     */
    List<UserProfileResponseDto> getAllProfiles();
}
