package doctor.app.appointment_service.service;

import doctor.app.appointment_service.dtos.AppointmentRequest;
import doctor.app.appointment_service.dtos.AppointmentResponse;
import doctor.app.appointment_service.mapper.AppointmentMapper;
import doctor.app.appointment_service.models.Appointment;
import doctor.app.appointment_service.repository.AppointmentRepository;
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
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    //----CRUD Operations----//
    public List<AppointmentResponse> getAllAppointments() {
        List<Appointment> list = appointmentRepository.findAll();

        List<AppointmentResponse> dtoList = appointmentMapper.toDtoList(list);

        dtoList.forEach(dto -> log.info("Appointment: {}", dto));

        return dtoList;
    }


    public AppointmentResponse getAppointmentById(String appointmentId) {
        Appointment appointment= appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));
        return appointmentMapper.toResponse(appointment);
    }

    public void cancelAppointment(String appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Appointment not found with id: " + appointmentId
                ));

        log.info("Cancelling appointment with id={}", appointmentId);

        appointmentRepository.delete(appointment);
        log.info("Appointment with id={} cancelled successfully", appointmentId);
    }


    //1-Se crea la cita
    //2-Se notifica al paciente usando notification-service
    //3-Se crea el pago
    public Appointment createAppointment(AppointmentRequest dto) {
        log.info("Creating appointment for patientId={} with doctorId={}",
                dto.getPatientId(), dto.getDoctorId());

        validateIfAppointmentExists(dto);

        Appointment appointment = appointmentMapper.toEntity(dto);

        log.info("Appointment created successfully for patientId={} with doctorId={}",
        dto.getPatientId(), dto.getDoctorId());

        try {
            Appointment newAppointment = appointmentRepository.save(appointment);
            log.info("New appointment created successfully with id={}", newAppointment.getAppointmentId());
            return newAppointment;

        } catch (DataIntegrityViolationException e) {
            log.error("Error creating appointment for patientId={} with doctorId={}",
                    dto.getPatientId(), dto.getDoctorId(), e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error creating appointment: " + e.getMessage()
            );
        }
    }

    public void validateIfAppointmentExists(AppointmentRequest dto) {
        String doctorId = dto.getDoctorId();
        String patientId = dto.getPatientId();
        if (appointmentRepository.findByDoctorId(doctorId).isPresent() &&
        appointmentRepository.findByPatientId(patientId).isPresent()){
            log.warn("Create rejected: appointment with doctorId={} and patientId={} already exists",
                    doctorId, patientId);
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Appointment already exists with doctorId: " + doctorId + " and patientId: " + patientId
            );
        }
    }


}
