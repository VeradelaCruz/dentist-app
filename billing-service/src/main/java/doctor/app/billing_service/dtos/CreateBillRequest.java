package doctor.app.billing_service.dtos;

import doctor.app.billing_service.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBillRequest {
    private String appointmentId;
    private PaymentMethod paymentMethod;
}