package doctor.app.billing_service.stripe.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    public String createPaymentIntent(BigDecimal amount, String currency) {

        try {
            Map<String, Object> params = new HashMap<>();

            params.put("amount", amount.multiply(BigDecimal.valueOf(100)).intValue());

            params.put("currency", currency.toLowerCase());

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            return paymentIntent.getId();

        } catch (StripeException e) {
            throw new RuntimeException("Error creating payment intent", e);
        }
    }
}