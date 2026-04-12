package doctor.app.treatment_service.repository;

import doctor.app.treatment_service.models.Treatment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TreatmentRepository extends MongoRepository<Treatment, String> {
}
