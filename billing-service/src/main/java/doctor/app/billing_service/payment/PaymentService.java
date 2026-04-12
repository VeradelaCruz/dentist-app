package doctor.app.billing_service.payment;

import doctor.app.billing_service.enums.PaymentStatus;
import doctor.app.billing_service.models.Bill;
import doctor.app.billing_service.stripe.service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final StripeService stripeService;

    public void processPayment(Bill bill) {

        String paymentIntentId = stripeService.createPaymentIntent(
                bill.getTotalAmount(),
                bill.getCurrency()
        );

        bill.setStripePaymentIntentId(paymentIntentId);
        bill.setStatus(PaymentStatus.PAYMENT_PENDING);
    }
}
