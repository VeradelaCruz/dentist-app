package doctor.app.patient_service.repository;

import doctor.app.patient_service.model.Patient;
import jakarta.validation.constraints.Email;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PatientRepository extends MongoRepository<Patient,String> {

    boolean existsByEmail(@Email(message = "Invalid email format") String email);

    boolean existsByPatientId(String patientId);

    Optional<Patient> findByPatientId(String patientId);

    void deleteByPatientId(String patientId);
}
