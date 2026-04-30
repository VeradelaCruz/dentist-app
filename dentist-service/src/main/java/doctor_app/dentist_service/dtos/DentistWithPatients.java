package doctor_app.dentist_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DentistWithPatients {
    ///Doctor
    private String doctorId;
    private String fullName;
    private String specialization;
    ///Patient with treatment
    private List<PatientWithTreatment> patientsWithTreatments;


}