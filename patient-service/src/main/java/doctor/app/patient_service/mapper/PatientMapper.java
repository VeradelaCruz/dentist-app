package doctor.app.patient_service.mapper;

import doctor.app.patient_service.dtos.PatientAddedRequest;
import doctor.app.patient_service.dtos.PatientResponse;
import doctor.app.patient_service.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(target = "id", ignore = true)
    Patient toEntity(PatientAddedRequest dto);

    @Mapping(target = "fullName", expression = "java(patient.getName() + \" \" + patient.getSurname())")
    @Mapping(target = "gender", expression = "java(patient.getGender().name())")
    @Mapping(target = "age", expression = "java(java.time.Period.between(patient.getDateOfBirth(), java.time.LocalDate.now()).getYears())")
    PatientResponse toResponse(Patient patient);

    List<PatientResponse> toDtoList(List<Patient> patients);
}
