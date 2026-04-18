package doctor.app.patient_service.exception;

public class PatientNotFound extends RuntimeException {
    public PatientNotFound(String id) {
        super("Patient with patientId: " + id + " not found");
    }
}
