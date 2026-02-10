package ups.edu.ec.proyect_backend.users.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import ups.edu.ec.proyect_backend.auth.entities.UserAuthEntity;
import ups.edu.ec.proyect_backend.technologies.entities.TechnologyEntity;
import ups.edu.ec.proyect_backend.users.dtos.developer.CreateDeveloperProfileDto;
import ups.edu.ec.proyect_backend.users.dtos.developer.DeveloperProfileResponseDto;
import ups.edu.ec.proyect_backend.users.dtos.developer.PatchDeveloperProfileDto;
import ups.edu.ec.proyect_backend.users.dtos.developer.UpdateDeveloperProfileDto;
import ups.edu.ec.proyect_backend.users.dtos.standard.CreateStandardProfileDto;
import ups.edu.ec.proyect_backend.users.dtos.standard.PatchStandardProfileDto;
import ups.edu.ec.proyect_backend.users.dtos.standard.StandardProfileResponseDto;
import ups.edu.ec.proyect_backend.users.dtos.standard.UpdateStandardProfileDto;
import ups.edu.ec.proyect_backend.users.entities.UserProfileEntity;

/**
 * Mapper para convertir entre DTOs y UserProfileEntity
 * Soporta ambos tipos de perfiles: STANDARD y DEVELOPER
 */
public class UserProfileMapper {

    // ============ STANDARD PROFILE MAPPERS ============

    /**
     * Convierte CreateStandardProfileDto a UserProfileEntity
     */
    public static UserProfileEntity fromCreateStandardDto(CreateStandardProfileDto dto) {
        UserProfileEntity profile = new UserProfileEntity();
        profile.setPhotoUrl(dto.getPhotoUrl());
        profile.setPhoneNumber(dto.getPhoneNumber());
        return profile;
    }

    /**
     * Convierte UpdateStandardProfileDto a UserProfileEntity
     */
    public static UserProfileEntity fromUpdateStandardDto(UpdateStandardProfileDto dto,
            UserProfileEntity profile) {
        profile.setPhotoUrl(dto.getPhotoUrl());
        profile.setPhoneNumber(dto.getPhoneNumber());
        return profile;
    }

    /**
     * Convierte PatchStandardProfileDto a UserProfileEntity (merge parcial)
     */
    public static UserProfileEntity fromPatchStandardDto(PatchStandardProfileDto dto,
            UserProfileEntity profile) {
        if (dto.getPhotoUrl() != null) {
            profile.setPhotoUrl(dto.getPhotoUrl());
        }
        if (dto.getPhoneNumber() != null) {
            profile.setPhoneNumber(dto.getPhoneNumber());
        }
        return profile;
    }

    /**
     * Convierte UserProfileEntity a StandardProfileResponseDto
     */
    public static StandardProfileResponseDto toStandardResponse(UserProfileEntity profile,
            UserAuthEntity userAuth) {
        return new StandardProfileResponseDto(
                profile.getId(),
                userAuth.getId(),
                userAuth.getEmail(),
                userAuth.getName(),
                profile.getPhotoUrl(),
                profile.getPhoneNumber());
    }

    // ============ DEVELOPER PROFILE MAPPERS ============

    /**
     * Convierte CreateDeveloperProfileDto a UserProfileEntity
     */
    public static UserProfileEntity fromCreateDeveloperDto(CreateDeveloperProfileDto dto) {
        UserProfileEntity profile = new UserProfileEntity();
        profile.setPhotoUrl(dto.getPhotoUrl());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setTitle(dto.getTitle());
        profile.setBio(dto.getBio());
        profile.setExperienceYears(dto.getExperienceYears());
        return profile;
    }

    /**
     * Convierte UpdateDeveloperProfileDto a UserProfileEntity
     */
    public static UserProfileEntity fromUpdateDeveloperDto(UpdateDeveloperProfileDto dto,
            UserProfileEntity profile) {
        profile.setPhotoUrl(dto.getPhotoUrl());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setTitle(dto.getTitle());
        profile.setBio(dto.getBio());
        profile.setExperienceYears(dto.getExperienceYears());
        return profile;
    }

    /**
     * Convierte PatchDeveloperProfileDto a UserProfileEntity (merge parcial)
     */
    public static UserProfileEntity fromPatchDeveloperDto(PatchDeveloperProfileDto dto,
            UserProfileEntity profile) {
        if (dto.getPhotoUrl() != null) {
            profile.setPhotoUrl(dto.getPhotoUrl());
        }
        if (dto.getPhoneNumber() != null) {
            profile.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getTitle() != null) {
            profile.setTitle(dto.getTitle());
        }
        if (dto.getBio() != null) {
            profile.setBio(dto.getBio());
        }
        if (dto.getExperienceYears() != null) {
            profile.setExperienceYears(dto.getExperienceYears());
        }
        return profile;
    }

    /**
     * Convierte UserProfileEntity a DeveloperProfileResponseDto con skills
     */
    public static DeveloperProfileResponseDto toDeveloperResponse(UserProfileEntity profile,
            UserAuthEntity userAuth) {
        Set<DeveloperProfileResponseDto.SkillResponseDto> skillDtos = profile.getSkills().stream()
                .map(skill -> new DeveloperProfileResponseDto.SkillResponseDto(
                        skill.getId(),
                        skill.getTechnology()))
                .collect(Collectors.toSet());

        return new DeveloperProfileResponseDto(
                profile.getId(),
                userAuth.getId(),
                userAuth.getEmail(),
                userAuth.getName(),
                profile.getPhotoUrl(),
                profile.getPhoneNumber(),
                profile.getTitle(),
                profile.getBio(),
                profile.getExperienceYears(),
                skillDtos);
    }

    /**
     * Asigna las skills a un perfil basado en una lista de IDs
     */
    public static void assignSkills(UserProfileEntity profile, Set<TechnologyEntity> technologies) {
        if (technologies != null) {
            profile.setSkills(technologies);
        }
    }
}
