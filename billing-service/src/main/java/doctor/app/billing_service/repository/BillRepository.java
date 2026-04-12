package doctor.app.billing_service.repository;

import doctor.app.billing_service.models.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface BillRepository extends MongoRepository<Bill,String> {
    Optional<Bill> findByStripePaymentIntentId(String stripePaymentIntentId);
}
