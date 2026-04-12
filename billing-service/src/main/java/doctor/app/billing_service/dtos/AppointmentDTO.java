package doctor.app.billing_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String appointmentDateTime;
    private String reason;
    private String status;
    private BigDecimal price;

}
