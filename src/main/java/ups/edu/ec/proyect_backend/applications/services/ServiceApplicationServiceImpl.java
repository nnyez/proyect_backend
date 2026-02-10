package ups.edu.ec.proyect_backend.applications.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ups.edu.ec.proyect_backend.applications.dtos.ApplicationResponseDto;
import ups.edu.ec.proyect_backend.applications.dtos.CreateApplicationDto;
import ups.edu.ec.proyect_backend.applications.dtos.UpdateApplicationStatusDto;
import ups.edu.ec.proyect_backend.applications.entities.ServiceApplicationEntity;
import ups.edu.ec.proyect_backend.applications.models.ApplicationStatus;
import ups.edu.ec.proyect_backend.applications.repositories.ServiceApplicationRepository;
import ups.edu.ec.proyect_backend.auth.entities.UserAuthEntity;
import ups.edu.ec.proyect_backend.mail.dtos.ApplicationAcceptedNotificationDto;
import ups.edu.ec.proyect_backend.mail.dtos.ApplicationRejectedNotificationDto;
import ups.edu.ec.proyect_backend.mail.dtos.NewApplicationNotificationDto;
import ups.edu.ec.proyect_backend.mail.services.MailService;
import ups.edu.ec.proyect_backend.users.entities.UserProfileEntity;
import ups.edu.ec.proyect_backend.users.repositories.UserProfileRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de solicitudes de servicio
 */
@Service
public class ServiceApplicationServiceImpl implements ServiceApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceApplicationServiceImpl.class);
    
    private final ServiceApplicationRepository applicationRepository;
    private final UserProfileRepository userRepository;
    private final MailService mailService;

    public ServiceApplicationServiceImpl(ServiceApplicationRepository applicationRepository,
                                        UserProfileRepository userRepository,
                                        MailService mailService) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public ApplicationResponseDto createApplication(Long clientId, CreateApplicationDto createDto) {
        // Verificar que el cliente existe
        UserProfileEntity client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clientId));

        // Verificar que el programador existe
        UserProfileEntity programmer = userRepository.findById(createDto.getProgrammerUid())
                .orElseThrow(() -> new RuntimeException("Programador no encontrado con ID: " + createDto.getProgrammerUid()));

        // Crear entidad
        ServiceApplicationEntity entity = new ServiceApplicationEntity();
        entity.setClient(client);
        entity.setClientName(getDisplayName(client));
        entity.setProgrammer(programmer);
        entity.setProgrammerName(getDisplayName(programmer));
        entity.setStatus(ApplicationStatus.PENDING);
        entity.setSubject(createDto.getSubject());
        entity.setDescription(createDto.getDescription());
        entity.setBudget(createDto.getBudget());
        entity.setScheduledDate(createDto.getScheduledDate());
        entity.setDurationMinutes(createDto.getDurationMinutes());
        entity.setStartTime(createDto.getStartTime());
        entity.setEndTime(createDto.getEndTime());
        
        Long now = System.currentTimeMillis();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        // Guardar
        ServiceApplicationEntity saved = applicationRepository.save(entity);

        // Enviar notificación por email al programador
        sendNewApplicationEmail(saved, client, programmer);

        return mapToResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationResponseDto getApplicationById(Long id) {
        ServiceApplicationEntity entity = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + id));

        return mapToResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponseDto> getApplicationsByClient(Long clientId) {
        // Verificar que el cliente existe
        if (!userRepository.existsById(clientId)) {
            throw new RuntimeException("Cliente no encontrado con ID: " + clientId);
        }

        List<ServiceApplicationEntity> entities = applicationRepository.findByClientId(clientId);
        return entities.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponseDto> getApplicationsByProgrammer(Long programmerId) {
        // Verificar que el programador existe
        if (!userRepository.existsById(programmerId)) {
            throw new RuntimeException("Programador no encontrado con ID: " + programmerId);
        }

        List<ServiceApplicationEntity> entities = applicationRepository.findByProgrammerId(programmerId);
        return entities.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponseDto> getApplicationsByClientAndStatus(Long clientId, ApplicationStatus status) {
        // Verificar que el cliente existe
        if (!userRepository.existsById(clientId)) {
            throw new RuntimeException("Cliente no encontrado con ID: " + clientId);
        }

        List<ServiceApplicationEntity> entities = applicationRepository.findByClientIdAndStatus(clientId, status);
        return entities.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponseDto> getApplicationsByProgrammerAndStatus(Long programmerId, ApplicationStatus status) {
        // Verificar que el programador existe
        if (!userRepository.existsById(programmerId)) {
            throw new RuntimeException("Programador no encontrado con ID: " + programmerId);
        }

        List<ServiceApplicationEntity> entities = applicationRepository.findByProgrammerIdAndStatus(programmerId, status);
        return entities.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ApplicationResponseDto updateApplicationStatus(Long id, UpdateApplicationStatusDto updateDto) {
        ServiceApplicationEntity entity = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + id));

        ApplicationStatus previousStatus = entity.getStatus();
        
        // Actualizar campos
        if (updateDto.getStatus() != null) {
            entity.setStatus(updateDto.getStatus());
        }
        if (updateDto.getMeetingLink() != null) {
            entity.setMeetingLink(updateDto.getMeetingLink());
        }
        if (updateDto.getRejectionReason() != null) {
            entity.setRejectionReason(updateDto.getRejectionReason());
        }
        
        entity.setUpdatedAt(System.currentTimeMillis());

        ServiceApplicationEntity updated = applicationRepository.save(entity);
        
        // Enviar notificaciones por email según el cambio de estado
        sendStatusChangeEmail(updated, previousStatus);
        
        return mapToResponseDto(updated);
    }

    @Override
    @Transactional
    public void deleteApplication(Long id) {
        if (!applicationRepository.existsById(id)) {
            throw new RuntimeException("Solicitud no encontrada con ID: " + id);
        }
        applicationRepository.deleteById(id);
    }

    /**
     * Mapea entidad a DTO de respuesta
     */
    private ApplicationResponseDto mapToResponseDto(ServiceApplicationEntity entity) {
        ApplicationResponseDto dto = new ApplicationResponseDto();
        dto.setId(entity.getId());
        dto.setClientUid(entity.getClient().getId());
        dto.setClientName(entity.getClientName());
        dto.setProgrammerUid(entity.getProgrammer().getId());
        dto.setProgrammerName(entity.getProgrammerName());
        dto.setStatus(entity.getStatus());
        dto.setSubject(entity.getSubject());
        dto.setDescription(entity.getDescription());
        dto.setBudget(entity.getBudget());
        dto.setScheduledDate(entity.getScheduledDate());
        dto.setDurationMinutes(entity.getDurationMinutes());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setMeetingLink(entity.getMeetingLink());
        dto.setRejectionReason(entity.getRejectionReason());
        return dto;
    }

    /**
     * Obtiene el nombre para mostrar del usuario
     */
    private String getDisplayName(UserProfileEntity user) {
        UserAuthEntity auth = user.getAuth();
        if (auth != null && auth.getName() != null && !auth.getName().isEmpty()) {
            return auth.getName();
        }
        return "Usuario " + user.getId();
    }

    /**
     * Envía notificación por email al programador cuando se crea una nueva solicitud
     */
    private void sendNewApplicationEmail(ServiceApplicationEntity application, 
                                        UserProfileEntity client, 
                                        UserProfileEntity programmer) {
        try {
            String programmerEmail = getEmailFromUser(programmer);
            if (programmerEmail != null) {
                String formattedDate = formatTimestamp(application.getScheduledDate());
                
                NewApplicationNotificationDto notification = new NewApplicationNotificationDto(
                    programmerEmail,
                    application.getClientName(),
                    application.getSubject(),
                    application.getDescription(),
                    formattedDate
                );
                
                mailService.sendNewApplicationNotification(notification);
                logger.info("Notificación de nueva solicitud enviada al programador: {}", programmerEmail);
            } else {
                logger.warn("No se pudo enviar email: Programador {} no tiene email configurado", programmer.getId());
            }
        } catch (Exception e) {
            logger.error("Error al enviar notificación de nueva solicitud: {}", e.getMessage());
            // No lanzamos excepción para no interrumpir el flujo principal
        }
    }

    /**
     * Envía notificación por email al cliente cuando cambia el estado de la solicitud
     */
    private void sendStatusChangeEmail(ServiceApplicationEntity application, ApplicationStatus previousStatus) {
        try {
            UserProfileEntity client = application.getClient();
            String clientEmail = getEmailFromUser(client);
            
            if (clientEmail == null) {
                logger.warn("No se pudo enviar email: Cliente {} no tiene email configurado", client.getId());
                return;
            }

            // Solo enviar email si el estado cambió a ACCEPTED o REJECTED
            if (application.getStatus() == ApplicationStatus.ACCEPTED && previousStatus != ApplicationStatus.ACCEPTED) {
                ApplicationAcceptedNotificationDto notification = new ApplicationAcceptedNotificationDto(
                    clientEmail,
                    application.getProgrammerName(),
                    application.getSubject(),
                    application.getMeetingLink()
                );
                
                mailService.sendApplicationAcceptedNotification(notification);
                logger.info("Notificación de solicitud aceptada enviada al cliente: {}", clientEmail);
                
            } else if (application.getStatus() == ApplicationStatus.REJECTED && previousStatus != ApplicationStatus.REJECTED) {
                ApplicationRejectedNotificationDto notification = new ApplicationRejectedNotificationDto(
                    clientEmail,
                    application.getProgrammerName(),
                    application.getSubject(),
                    application.getRejectionReason()
                );
                
                mailService.sendApplicationRejectedNotification(notification);
                logger.info("Notificación de solicitud rechazada enviada al cliente: {}", clientEmail);
            }
        } catch (Exception e) {
            logger.error("Error al enviar notificación de cambio de estado: {}", e.getMessage());
            // No lanzamos excepción para no interrumpir el flujo principal
        }
    }

    /**
     * Obtiene el email del usuario desde su entidad de autenticación
     */
    private String getEmailFromUser(UserProfileEntity user) {
        UserAuthEntity auth = user.getAuth();
        if (auth != null && auth.getEmail() != null && !auth.getEmail().isEmpty()) {
            return auth.getEmail();
        }
        return null;
    }

    /**
     * Formatea un timestamp a formato legible
     */
    private String formatTimestamp(Long timestamp) {
        if (timestamp == null) {
            return "No especificada";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(new Date(timestamp));
    }
}
