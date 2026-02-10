package ups.edu.ec.proyect_backend.mail.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import ups.edu.ec.proyect_backend.mail.MailProperties;
import ups.edu.ec.proyect_backend.mail.dtos.ApplicationAcceptedNotificationDto;
import ups.edu.ec.proyect_backend.mail.dtos.ApplicationRejectedNotificationDto;
import ups.edu.ec.proyect_backend.mail.dtos.NewApplicationNotificationDto;
import ups.edu.ec.proyect_backend.mail.dtos.SendMailRequestDto;

/**
 * Servicio para gestionar el envío de correos electrónicos
 * Implementa las funcionalidades de notificación por email
 */
@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);
    private static final String MAIL_LOG_PREFIX = "[MAIL SERVICE]";

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    public MailService(JavaMailSender mailSender, MailProperties mailProperties) {
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
        logger.info("{} Service initialized with properties: receiverEmail = {}", MAIL_LOG_PREFIX, mailProperties.getReceiverEmail());
    }

    /**
     * Envía un correo electrónico genérico
     * Soporta tanto texto plano como HTML
     */
    public void sendMail(SendMailRequestDto request) throws MessagingException {
        logger.info("{} Attempting to send email to: {}", MAIL_LOG_PREFIX, request.getSendTo());
        logger.info("{} Subject: {}", MAIL_LOG_PREFIX, request.getSubject());

        try {
            if (request.getHtml() != null && !request.getHtml().isEmpty()) {
                sendHtmlMail(request.getSendTo(), request.getSubject(), request.getHtml());
            } else {
                sendTextMail(request.getSendTo(), request.getSubject(), request.getText());
            }
            logger.info("{} ✓ Email sent successfully to: {}", MAIL_LOG_PREFIX, request.getSendTo());
        } catch (Exception e) {
            logger.error("{} ✗ Error sending email to {}: {}", MAIL_LOG_PREFIX, request.getSendTo(), e.getMessage());
            throw e;
        }
    }

    /**
     * Envía un correo de texto plano
     */
    private void sendTextMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    /**
     * Envía un correo con contenido HTML
     */
    private void sendHtmlMail(String to, String subject, String html) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);

        mailSender.send(message);
    }

    /**
     * Envía notificación de nueva solicitud de servicio al programador
     */
    public void sendNewApplicationNotification(NewApplicationNotificationDto notification) throws MessagingException {
        logger.info("{} sendNewApplicationNotification - Notifying programmer: {}", MAIL_LOG_PREFIX, notification.getProgrammerEmail());

        String html = buildNewApplicationHtml(
                notification.getClientName(),
                notification.getSubject(),
                notification.getDescription(),
                notification.getScheduledDate()
        );

        SendMailRequestDto mailRequest = new SendMailRequestDto(
                notification.getProgrammerEmail(),
                "Nueva solicitud de servicio: " + notification.getSubject(),
                "Nueva solicitud de " + notification.getClientName() + " - " + notification.getSubject(),
                html
        );

        try {
            sendMail(mailRequest);
            logger.info("{} ✓ Notification sent to programmer successfully", MAIL_LOG_PREFIX);
        } catch (Exception e) {
            logger.error("{} ✗ Failed to send notification to programmer: {}", MAIL_LOG_PREFIX, e.getMessage());
            throw e;
        }
    }

    /**
     * Envía notificación de solicitud aceptada al cliente
     */
    public void sendApplicationAcceptedNotification(ApplicationAcceptedNotificationDto notification) throws MessagingException {
        logger.info("{} sendApplicationAcceptedNotification - Notifying client: {}", MAIL_LOG_PREFIX, notification.getClientEmail());

        String html = buildApplicationAcceptedHtml(
                notification.getProgrammerName(),
                notification.getSubject(),
                notification.getMeetingLink()
        );

        SendMailRequestDto mailRequest = new SendMailRequestDto(
                notification.getClientEmail(),
                "Solicitud aceptada: " + notification.getSubject(),
                "Tu solicitud ha sido aceptada por " + notification.getProgrammerName(),
                html
        );

        try {
            sendMail(mailRequest);
            logger.info("{} ✓ Acceptance notification sent to client successfully", MAIL_LOG_PREFIX);
        } catch (Exception e) {
            logger.error("{} ✗ Failed to send acceptance notification to client: {}", MAIL_LOG_PREFIX, e.getMessage());
            throw e;
        }
    }

    /**
     * Envía notificación de solicitud rechazada al cliente
     */
    public void sendApplicationRejectedNotification(ApplicationRejectedNotificationDto notification) throws MessagingException {
        logger.info("{} sendApplicationRejectedNotification - Notifying client: {}", MAIL_LOG_PREFIX, notification.getClientEmail());

        String html = buildApplicationRejectedHtml(
                notification.getProgrammerName(),
                notification.getSubject(),
                notification.getReason()
        );

        SendMailRequestDto mailRequest = new SendMailRequestDto(
                notification.getClientEmail(),
                "Solicitud rechazada: " + notification.getSubject(),
                "Tu solicitud ha sido rechazada por " + notification.getProgrammerName(),
                html
        );

        try {
            sendMail(mailRequest);
            logger.info("{} ✓ Rejection notification sent to client successfully", MAIL_LOG_PREFIX);
        } catch (Exception e) {
            logger.error("{} ✗ Failed to send rejection notification to client: {}", MAIL_LOG_PREFIX, e.getMessage());
            throw e;
        }
    }

    /**
     * Construye el HTML para notificación de nueva solicitud
     */
    private String buildNewApplicationHtml(String clientName, String subject, String description, String date) {
        return String.format(
                "<h2>Nueva Solicitud de Servicio</h2>" +
                        "<p><strong>Cliente:</strong> %s</p>" +
                        "<p><strong>Asunto:</strong> %s</p>" +
                        "<p><strong>Descripción:</strong> %s</p>" +
                        "<p><strong>Fecha Programada:</strong> %s</p>" +
                        "<p>Por favor accede a tu panel para revisar los detalles y aceptar o rechazar la solicitud.</p>",
                clientName, subject, description, date
        );
    }

    /**
     * Construye el HTML para notificación de solicitud aceptada
     */
    private String buildApplicationAcceptedHtml(String programmerName, String subject, String meetingLink) {
        StringBuilder html = new StringBuilder();
        html.append("<h2>Solicitud Aceptada</h2>")
                .append("<p><strong>Programador:</strong> ").append(programmerName).append("</p>")
                .append("<p><strong>Asunto:</strong> ").append(subject).append("</p>")
                .append("<p>¡Tu solicitud ha sido aceptada!</p>");

        if (meetingLink != null && !meetingLink.isEmpty()) {
            html.append("<p><strong>Enlace de reunión:</strong> <a href=\"")
                    .append(meetingLink).append("\">")
                    .append(meetingLink).append("</a></p>");
        }

        html.append("<p>El programador se pondrá en contacto contigo próximamente.</p>");
        return html.toString();
    }

    /**
     * Construye el HTML para notificación de solicitud rechazada
     */
    private String buildApplicationRejectedHtml(String programmerName, String subject, String reason) {
        StringBuilder html = new StringBuilder();
        html.append("<h2>Solicitud Rechazada</h2>")
                .append("<p><strong>Programador:</strong> ").append(programmerName).append("</p>")
                .append("<p><strong>Asunto:</strong> ").append(subject).append("</p>")
                .append("<p>Lamentablemente, tu solicitud ha sido rechazada.</p>");

        if (reason != null && !reason.isEmpty()) {
            html.append("<p><strong>Motivo:</strong> ").append(reason).append("</p>");
        }

        html.append("<p>Por favor, intenta con otro programador o revisa tus requisitos.</p>");
        return html.toString();
    }
}
