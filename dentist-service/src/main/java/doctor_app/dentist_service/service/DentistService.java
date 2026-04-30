package doctor_app.dentist_service.service;

import doctor_app.dentist_service.client.PatientServiceClient;
import doctor_app.dentist_service.client.TreatmentServiceClient;
import doctor_app.dentist_service.dtos.DentistAddRequest;
import doctor_app.dentist_service.dtos.DentistResponse;
import doctor_app.dentist_service.dtos.DentistWithPatients;
import doctor_app.dentist_service.dtos.PatientSimple;
import doctor_app.dentist_service.dtos.PatientWithTreatment;
import doctor_app.dentist_service.dtos.TreatmentResponse;
import doctor_app.dentist_service.mapper.DentistMapper;
import doctor_app.dentist_service.models.Dentist;
import doctor_app.dentist_service.repository.DentistRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DentistService {

    private final DentistRepository dentistRepository;
    private final DentistMapper dentistMapper;

    public DentistResponse getById(String id) {
        Dentist dentist= dentistRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Dentist not found with id: " + id
                ));
        return dentistMapper.toResponse(dentist);
    }

    public List<DentistResponse> getAll() {
        List<Dentist> dentists = dentistRepository.findAll();
        List<DentistResponse> dtoList = dentistMapper.toDtoList(dentists);

        dtoList.forEach(dto -> log.info("Dentists: {}", dto));

        return dtoList;
    }

    public Dentist saveDentist(DentistAddRequest dto) {

        validateEmailUniqueness(dto.getEmail());

        Dentist dentist = dentistMapper.toEntity(dto);

        log.info("Creating dentist with email={}", dentist.getEmail());

        try {
            Dentist saved = dentistRepository.save(dentist);
            log.info("Dentist created successfully with id={}", saved.getDoctorId());
            return saved;

        } catch (DataIntegrityViolationException e) {
            log.error("Constraint violation for email={}", dto.getEmail(), e);
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Dentist with this email already exists"
            );
        }
    }

    private void validateEmailUniqueness(String email) {
        if (dentistRepository.existsByEmail(email)) {
            log.warn("Create rejected: dentist with email={} already exists", email);
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Dentist already exists with email: " + email
            );
        }
    }

    public void deleteDentist(String doctorId) {

        Dentist dentist = dentistRepository.findById(doctorId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Dentist not found with doctorId: " + doctorId
                ));

        log.info("Deleting dentist with doctorId={}", doctorId);

        dentistRepository.delete(dentist);

        log.info("Dentist with doctorId={} deleted successfully", doctorId);
    }

    private final PatientServiceClient patientServiceClient;
    private final TreatmentServiceClient treatmentServiceClient;

    public DentistWithPatients getDentistWithPatients(String doctorId) {
        // 1. Obtener el dentista
        Dentist dentist = dentistRepository.findById(doctorId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Dentist not found with id: " + doctorId
                ));

        // 2. Obtener pacientes del doctor usando Feign
        List<PatientSimple> patients = patientServiceClient.getPatientsByDoctorId(doctorId);

        // 3. Para cada paciente, obtener tratamientos usando Feign
        List<PatientWithTreatment> patientsWithTreatments = new ArrayList<>();
        for (PatientSimple patient : patients) {
            List<TreatmentResponse> treatments = treatmentServiceClient.getTreatmentsByPatientId(patient.getPatientId());
            PatientWithTreatment patientWithTreatment = new PatientWithTreatment(
                patient.getPatientId(), patient.getName(), patient.getSurname(), treatments
            );
            patientsWithTreatments.add(patientWithTreatment);
        }

        // 4. Retornar DTO
        return new DentistWithPatients(
            dentist.getDoctorId(), dentist.getFirstName() + " " + dentist.getLastName(), dentist.getSpecialization(),
            patientsWithTreatments
        );
    }
}
