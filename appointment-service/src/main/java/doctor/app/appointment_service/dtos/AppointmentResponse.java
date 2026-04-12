package doctor.app.appointment_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {
    private String patientId;
    private String doctorId;
    private String appointmentDateTime;
    private String reason;

}
