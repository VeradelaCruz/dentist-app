package doctor_app.dentist_service.mapper;


import doctor_app.dentist_service.dtos.DentistAddRequest;
import doctor_app.dentist_service.dtos.DentistResponse;
import doctor_app.dentist_service.models.Dentist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DentistMapper {
    Dentist toEntity(DentistAddRequest dto);

    @Mapping(target = "fullName", expression = "java(dentist.getFirstName() + \" \" + dentist.getLastName())")
    DentistResponse toResponse(Dentist dentist);

    List<DentistResponse> toDtoList(List<Dentist> dentists);
}