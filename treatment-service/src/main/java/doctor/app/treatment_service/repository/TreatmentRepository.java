package doctor.app.treatment_service.repository;

import doctor.app.treatment_service.models.Treatment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TreatmentRepository extends MongoRepository<Treatment, String> {
    List<Treatment> findByPatientId(String patientId);
    List<Treatment> findByDentistId(String dentistId);
}
