package doctor_app.dentist_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientWithTreatment {
    private String patientId;
    private String name;
    private String surname;
    private List<TreatmentResponse> treatments;
}
