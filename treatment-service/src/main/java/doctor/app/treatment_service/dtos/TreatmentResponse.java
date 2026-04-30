package doctor.app.treatment_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentResponse {
    private String treatmentId;
    private String name;
    private String description;
}
