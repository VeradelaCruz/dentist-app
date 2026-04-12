package doctor.app.billing_service.mapper;

import doctor.app.billing_service.dtos.BillResponse;
import doctor.app.billing_service.models.Bill;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BillMapper {
    Bill toEntity(BillResponse billResponse);

    BillResponse toDto(Bill bill);

    List<BillResponse> toDtoList(List<Bill> bills);
}
