package ups.edu.ec.proyect_backend.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Clase para mapear las propiedades de configuración del servicio de mail
 * Desde application.yaml bajo la sección "mail.service"
 */
@Component
@ConfigurationProperties(prefix = "mail.service")
public class MailProperties {

    private String receiverEmail;

    // Getters y Setters
    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }
}
