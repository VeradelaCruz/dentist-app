package doctor.app.treatment_service.exception;

public class TreatmentNotFound extends RuntimeException {
    public TreatmentNotFound(String id) {
        super("Treatment with id=" + id + " not found");
    }
}
