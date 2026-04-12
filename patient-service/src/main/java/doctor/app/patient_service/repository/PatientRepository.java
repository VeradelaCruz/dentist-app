package doctor.app.patient_service.repository;

import doctor.app.patient_service.model.Patient;
import jakarta.validation.constraints.Email;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientRepository extends MongoRepository<Patient,String> {

    boolean existsByEmail(@Email(message = "Invalid email format") String email);
}
