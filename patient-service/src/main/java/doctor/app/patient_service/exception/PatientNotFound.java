package doctor.app.patient_service.exception;

public class PatientNotFound extends RuntimeException {
    public PatientNotFound(String id) {
        super("Patient with id: " + id + " not found");
    }
}
