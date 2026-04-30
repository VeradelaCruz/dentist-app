package doctor.app.patient_service.repository;

import doctor.app.patient_service.model.Patient;
import jakarta.validation.constraints.Email;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends MongoRepository<Patient,String> {

    boolean existsByEmail(@Email(message = "Invalid email format") String email);

    boolean existsByPatientId(String patientId);

    Optional<Patient> findByPatientId(String patientId);

    List<Patient> findAllByPatientIdIn(List<String> patientIds);

    void deleteByPatientId(String patientId);
}
