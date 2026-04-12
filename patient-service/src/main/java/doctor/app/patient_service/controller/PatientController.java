package doctor.app.patient_service.controller;

import doctor.app.patient_service.dtos.PatientAddedRequest;
import doctor.app.patient_service.dtos.PatientResponse;
import doctor.app.patient_service.mapper.PatientMapper;
import doctor.app.patient_service.model.Patient;
import doctor.app.patient_service.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

	private final PatientService patientService;
	private final PatientMapper patientMapper;

	@GetMapping
	public ResponseEntity<List<PatientResponse>> getAllPatients() {
		return ResponseEntity.ok(patientService.getAllPatients());
	}

	@GetMapping("/{patientId}")
	public ResponseEntity<PatientResponse> getPatientById(@PathVariable String patientId) {
		PatientResponse response = patientService.getPatientById(patientId);
		return ResponseEntity.ok(response);
	}


	@PostMapping
	public ResponseEntity<Patient> addPatient(@Valid @RequestBody PatientAddedRequest dto) {
		Patient created = patientService.createPatient(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@DeleteMapping("/{patientId}")
	public ResponseEntity<Void> deletePatient(@PathVariable String patientId) {
		patientService.deleteById(patientId);
		return ResponseEntity.noContent().build();
	}
}

