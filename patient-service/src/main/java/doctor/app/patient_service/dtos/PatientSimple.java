package doctor.app.patient_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientSimple {
    private String patientId;
    private String name;
    private String surname;
}
