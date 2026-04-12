package doctor.app.billing_service.stripe.webhook;

import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import doctor.app.billing_service.repository.BillRepository;
import doctor.app.billing_service.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook/stripe")
@RequiredArgsConstructor
public class StripeWebhookController {

    @Value("${stripe.webhook-secret}")
    private String endpointSecret;
    private final BillService billService;
    private final BillRepository billRepository;

    private void handleEvent(Event event) {

        if ("payment_intent.succeeded".equals(event.getType())) {
            PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();
            billService.updateBill(paymentIntent);
        } else if ("charge.refunded".equals(event.getType())) {
            System.out.println("Pago reembolsado");
        } else {
            System.out.println("Evento no manejado: " + event.getType());
        }
    }

    @PostMapping
    public ResponseEntity<Void> handleWebhook(@RequestBody String payload,
                                              @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (Exception e) {
            throw new RuntimeException("Invalid webhook", e);
        }

        handleEvent(event);

        return ResponseEntity.ok().build();
    }
}
