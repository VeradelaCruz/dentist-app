package doctor.app.treatment_service.service;

import doctor.app.treatment_service.exception.TreatmentNotFound;
import doctor.app.treatment_service.models.Treatment;
import doctor.app.treatment_service.repository.TreatmentRepository;
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
}


