package doctor.app.patient_service.service;

import doctor.app.patient_service.dtos.PatientAddedRequest;
import doctor.app.patient_service.dtos.PatientResponse;
import doctor.app.patient_service.exception.PatientNotFound;
import doctor.app.patient_service.mapper.PatientMapper;
import doctor.app.patient_service.model.Patient;
import doctor.app.patient_service.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatientService {

	private final PatientRepository patientRepository;
	private final PatientMapper patientMapper;

	//---CRUD OPERATIONS----//

	public PatientResponse getPatientById(String patientId) {
		Patient patient = patientRepository.findByPatientId(patientId)
				.orElseThrow(() -> new PatientNotFound(patientId));
		return patientMapper.toResponse(patient);
	}

	public List<PatientResponse> getAllPatients() {
		List<Patient> patients= patientRepository.findAll();
		List<PatientResponse> responseList = patientMapper.toDtoList(patients);
		responseList.forEach(dto -> log.info("Patients: {}", dto));
		return responseList;
	}

	public Patient createPatient(PatientAddedRequest dto) {

		validatePatientIdUniqueness(dto.getPatientId());
		validateEmailUniqueness(dto.getEmail());

		Patient patient = patientMapper.toEntity(dto);

		log.info("Creating patient with email={}", patient.getEmail());

		try {
			Patient saved = patientRepository.save(patient);
			log.info("Patient created successfully with id={}", saved.getPatientId());
			return saved;

		} catch (DataIntegrityViolationException e) {
			log.error("Constraint violation for email={}", dto.getEmail(), e);
			throw new ResponseStatusException(
					HttpStatus.CONFLICT,
					"Patient with this email already exists"
			);
		}
	}

	private void validateEmailUniqueness(String email) {
		if (email != null && patientRepository.existsByEmail(email)) {
			log.warn("Create rejected: patient with email={} already exists", email);
			throw new ResponseStatusException(
					HttpStatus.CONFLICT,
					"Patient already exists with email: " + email
			);
		}
	}

	private void validatePatientIdUniqueness(String patientId) {
		if (patientId != null && patientRepository.existsByPatientId(patientId)) {
			log.warn("Create rejected: patient with patientId={} already exists", patientId);
			throw new ResponseStatusException(
					HttpStatus.CONFLICT,
					"Patient already exists with patientId: " + patientId
			);
		}
	}

	public void deleteById(String patientId) {
		log.info("Deleting patient, patientId={}", patientId);

		if (!patientRepository.existsByPatientId(patientId)) {
			throw new PatientNotFound(patientId);
		}

		patientRepository.deleteByPatientId(patientId);

		log.info("Patient with patientId={} deleted successfully", patientId);
	}
}
