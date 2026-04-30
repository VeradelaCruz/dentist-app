package doctor.app.treatment_service.service;

import doctor.app.treatment_service.exception.TreatmentNotFound;
import doctor.app.treatment_service.models.Treatment;
import doctor.app.treatment_service.repository.TreatmentRepository;
import doctor.app.treatment_service.dtos.TreatmentResponse;
import doctor.app.treatment_service.dtos.TreatmentSimple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class TreatmentService {
    @Autowired
    private TreatmentRepository treatmentRepository;

    public Treatment getTreatmentById(String id) {
        log.info("Fetching treatment with id: {}", id);
        return treatmentRepository.findById(id)
                .orElseThrow(() -> new TreatmentNotFound("Treatment not found with id: " + id));
    }

    public List<Treatment> getAllTreatments() {
        log.info("Fetching all treatments");
        return treatmentRepository.findAll();
    }

    public Treatment createTreatment(Treatment treatment) {
        log.info("Creating new treatment for patient: {}", treatment.getPatientId());
        return treatmentRepository.save(treatment);
    }

    public void deleteTreatment(String id) {
        log.info("Deleting treatment with id: {}", id);
        if (!treatmentRepository.existsById(id)) {
            throw new TreatmentNotFound("Treatment not found with id: " + id);
        }
        treatmentRepository.deleteById(id);
    }

    public List<TreatmentResponse> getTreatmentsByPatientId(String patientId) {
        log.info("Fetching treatments for patientId: {}", patientId);
        List<Treatment> treatments = treatmentRepository.findByPatientId(patientId);
        return treatments.stream()
                .map(t -> new TreatmentResponse(t.getId(), t.getName(), t.getDescription()))
                .toList();
    }

    public List<TreatmentSimple> getTreatmentsByDentistId(String dentistId) {
        log.info("Fetching treatments for dentistId: {}", dentistId);
        List<Treatment> treatments = treatmentRepository.findByDentistId(dentistId);
        return treatments.stream()
                .map(t -> new TreatmentSimple(t.getPatientId(), t.getDentistId()))
                .toList();
    }
}
