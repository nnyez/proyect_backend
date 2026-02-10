package ups.edu.ec.proyect_backend.mail.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import ups.edu.ec.proyect_backend.mail.dtos.ApplicationAcceptedNotificationDto;
import ups.edu.ec.proyect_backend.mail.dtos.ApplicationRejectedNotificationDto;
import ups.edu.ec.proyect_backend.mail.dtos.MailResponseDto;
import ups.edu.ec.proyect_backend.mail.dtos.NewApplicationNotificationDto;
import ups.edu.ec.proyect_backend.mail.dtos.SendMailRequestDto;
import ups.edu.ec.proyect_backend.mail.services.MailService;

/**
 * Controlador REST para gestionar el envío de correos electrónicos
 * Endpoints disponibles en /api/mail
 */
@RestController
@RequestMapping("/api/mail")
public class MailController {

    private static final Logger logger = LoggerFactory.getLogger(MailController.class);
    private static final String MAIL_CONTROLLER_LOG_PREFIX = "[MAIL CONTROLLER]";

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * Envía un correo electrónico genérico
     * POST /api/mail/send
     */
    @PostMapping("/send")
    public ResponseEntity<MailResponseDto> sendMail(@Valid @RequestBody SendMailRequestDto request) {
        logger.info("{} POST /send - Sending mail to: {}", MAIL_CONTROLLER_LOG_PREFIX, request.getSendTo());

        try {
            mailService.sendMail(request);
            MailResponseDto response = new MailResponseDto(true, "Correo enviado exitosamente");
            logger.info("{} ✓ Mail sent successfully", MAIL_CONTROLLER_LOG_PREFIX);
            return ResponseEntity.ok(response);
        } catch (MessagingException e) {
            logger.error("{} ✗ Error sending mail: {}", MAIL_CONTROLLER_LOG_PREFIX, e.getMessage());
            MailResponseDto response = new MailResponseDto(false, "Error al enviar el correo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            logger.error("{} ✗ Unexpected error: {}", MAIL_CONTROLLER_LOG_PREFIX, e.getMessage());
            MailResponseDto response = new MailResponseDto(false, "Error inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Envía notificación de nueva solicitud de servicio al programador
     * POST /api/mail/notify/new-application
     */
    @PostMapping("/notify/new-application")
    public ResponseEntity<MailResponseDto> notifyNewApplication(@Valid @RequestBody NewApplicationNotificationDto notification) {
        logger.info("{} POST /notify/new-application - Notifying programmer: {}", MAIL_CONTROLLER_LOG_PREFIX, notification.getProgrammerEmail());

        try {
            mailService.sendNewApplicationNotification(notification);
            MailResponseDto response = new MailResponseDto(true, "Notificación de nueva solicitud enviada al programador");
            logger.info("{} ✓ New application notification sent successfully", MAIL_CONTROLLER_LOG_PREFIX);
            return ResponseEntity.ok(response);
        } catch (MessagingException e) {
            logger.error("{} ✗ Error sending new application notification: {}", MAIL_CONTROLLER_LOG_PREFIX, e.getMessage());
            MailResponseDto response = new MailResponseDto(false, "Error al enviar notificación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            logger.error("{} ✗ Unexpected error: {}", MAIL_CONTROLLER_LOG_PREFIX, e.getMessage());
            MailResponseDto response = new MailResponseDto(false, "Error inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Envía notificación de solicitud aceptada al cliente
     * POST /api/mail/notify/application-accepted
     */
    @PostMapping("/notify/application-accepted")
    public ResponseEntity<MailResponseDto> notifyApplicationAccepted(@Valid @RequestBody ApplicationAcceptedNotificationDto notification) {
        logger.info("{} POST /notify/application-accepted - Notifying client: {}", MAIL_CONTROLLER_LOG_PREFIX, notification.getClientEmail());

        try {
            mailService.sendApplicationAcceptedNotification(notification);
            MailResponseDto response = new MailResponseDto(true, "Notificación de aceptación enviada al cliente");
            logger.info("{} ✓ Application accepted notification sent successfully", MAIL_CONTROLLER_LOG_PREFIX);
            return ResponseEntity.ok(response);
        } catch (MessagingException e) {
            logger.error("{} ✗ Error sending acceptance notification: {}", MAIL_CONTROLLER_LOG_PREFIX, e.getMessage());
            MailResponseDto response = new MailResponseDto(false, "Error al enviar notificación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            logger.error("{} ✗ Unexpected error: {}", MAIL_CONTROLLER_LOG_PREFIX, e.getMessage());
            MailResponseDto response = new MailResponseDto(false, "Error inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Envía notificación de solicitud rechazada al cliente
     * POST /api/mail/notify/application-rejected
     */
    @PostMapping("/notify/application-rejected")
    public ResponseEntity<MailResponseDto> notifyApplicationRejected(@Valid @RequestBody ApplicationRejectedNotificationDto notification) {
        logger.info("{} POST /notify/application-rejected - Notifying client: {}", MAIL_CONTROLLER_LOG_PREFIX, notification.getClientEmail());

        try {
            mailService.sendApplicationRejectedNotification(notification);
            MailResponseDto response = new MailResponseDto(true, "Notificación de rechazo enviada al cliente");
            logger.info("{} ✓ Application rejected notification sent successfully", MAIL_CONTROLLER_LOG_PREFIX);
            return ResponseEntity.ok(response);
        } catch (MessagingException e) {
            logger.error("{} ✗ Error sending rejection notification: {}", MAIL_CONTROLLER_LOG_PREFIX, e.getMessage());
            MailResponseDto response = new MailResponseDto(false, "Error al enviar notificación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            logger.error("{} ✗ Unexpected error: {}", MAIL_CONTROLLER_LOG_PREFIX, e.getMessage());
            MailResponseDto response = new MailResponseDto(false, "Error inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
