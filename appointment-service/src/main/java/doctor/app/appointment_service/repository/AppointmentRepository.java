package doctor.app.appointment_service.repository;

import doctor.app.appointment_service.models.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AppointmentRepository extends MongoRepository<Appointment,String> {
    Optional<Object> findByDoctorId(String doctorId);
    Optional<Object> findByPatientId(String patientId);
}
