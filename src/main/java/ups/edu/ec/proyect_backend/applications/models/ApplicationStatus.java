package ups.edu.ec.proyect_backend.applications.models;

/**
 * Enum que representa los posibles estados de una solicitud de servicio
 */
public enum ApplicationStatus {
    PENDING,    // Pendiente de respuesta
    ACCEPTED,   // Aceptada por el programador
    REJECTED,   // Rechazada por el programador
    COMPLETED,  // Completada/finalizada
    CANCELLED   // Cancelada por el cliente
}
