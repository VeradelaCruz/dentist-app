package doctor.app.billing_service.models;

import doctor.app.billing_service.enums.PaymentMethod;
import doctor.app.billing_service.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(collection = "bills")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {

    @Id
    private String id;
    private String fullName;
    private BigDecimal totalAmount;
    private String currency;
    private PaymentMethod paymentMethod;
    private String appointmentId;

    private PaymentStatus status;
    //Stripe fields
    private String stripePaymentIntentId;
    private String stripeCustomerId;
    // Auditory fields
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;

}
