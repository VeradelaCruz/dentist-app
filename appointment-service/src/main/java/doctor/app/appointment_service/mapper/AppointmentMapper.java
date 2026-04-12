package doctor.app.appointment_service.mapper;

import doctor.app.appointment_service.dtos.AppointmentRequest;
import doctor.app.appointment_service.dtos.AppointmentResponse;
import doctor.app.appointment_service.models.Appointment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    Appointment toEntity(AppointmentRequest dto);
    AppointmentResponse toResponse(Appointment appointment);
    List<AppointmentResponse> toDtoList(List<Appointment> appointments);
    AppointmentRequest toRequest(Appointment appointment);


}
