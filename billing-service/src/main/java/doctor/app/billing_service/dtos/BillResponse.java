package doctor.app.billing_service.dtos;

import doctor.app.billing_service.enums.PaymentMethod;
import doctor.app.billing_service.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillResponse {
    private String id;
    private String fullName;
    private Double totalAmount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatement;
    private LocalDate createdAt;

}