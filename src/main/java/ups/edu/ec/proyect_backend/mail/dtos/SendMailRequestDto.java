package ups.edu.ec.proyect_backend.mail.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para solicitudes genéricas de envío de correos electrónicos
 */
public class SendMailRequestDto {

    @Email(message = "El destinatario debe ser un correo válido")
    @NotBlank(message = "El destinatario no puede estar vacío")
    private String sendTo;

    @NotBlank(message = "El asunto no puede estar vacío")
    private String subject;

    @NotBlank(message = "El cuerpo del correo (text) no puede estar vacío")
    private String text;

    private String html;

    // Constructores
    public SendMailRequestDto() {
    }

    public SendMailRequestDto(String sendTo, String subject, String text, String html) {
        this.sendTo = sendTo;
        this.subject = subject;
        this.text = text;
        this.html = html;
    }

    public SendMailRequestDto(String sendTo, String subject, String text) {
        this.sendTo = sendTo;
        this.subject = subject;
        this.text = text;
    }

    // Getters y Setters
    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
