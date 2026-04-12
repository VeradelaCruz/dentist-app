package doctor_app.dentist_service.repository;

import doctor_app.dentist_service.models.Dentist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface DentistRepository extends MongoRepository<Dentist, String> {
    boolean existsByEmail(@Email(message = "Invalid email format") @NotBlank(message = "Email cannot be blank") String email);
}
