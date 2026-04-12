package doctor.app.appointment_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "appointments")
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String appointmentDateTime;
    private String reason;
    private String status;
    private BigDecimal price;

}
