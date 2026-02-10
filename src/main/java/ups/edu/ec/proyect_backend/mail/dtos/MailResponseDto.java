package ups.edu.ec.proyect_backend.mail.dtos;

/**
 * DTO para la respuesta del servicio de mail
 */
public class MailResponseDto {

    private boolean success;
    private String message;
    private String messageId;

    // Constructores
    public MailResponseDto() {
    }

    public MailResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public MailResponseDto(boolean success, String message, String messageId) {
        this.success = success;
        this.message = message;
        this.messageId = messageId;
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
