package doctor.app.billing_service.service;

import com.stripe.model.PaymentIntent;
import doctor.app.billing_service.dtos.AppointmentDTO;
import doctor.app.billing_service.dtos.BillResponse;
import doctor.app.billing_service.dtos.CreateBillRequest;
import doctor.app.billing_service.dtos.PatientDTO;
import doctor.app.billing_service.enums.PaymentStatus;
import doctor.app.billing_service.feign.AppointmentClient;
import doctor.app.billing_service.feign.PatientClient;
import doctor.app.billing_service.mapper.BillMapper;
import doctor.app.billing_service.models.Bill;
import doctor.app.billing_service.payment.PaymentService;
import doctor.app.billing_service.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private final BillMapper billMapper;
    private final AppointmentClient appointmentClient;
    private final PatientClient patientClient;
    private final PaymentService paymentService;

    ///----CRUD OPERATIONS----///
    public BillResponse getBillById(String id) {
        Bill bill = billRepository.findById(id).orElseThrow(()->new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Bill not found with id: " + id
        ));
        return billMapper.toDto(bill);
    }

    public List<BillResponse> getAllBills() {
        List<Bill> bills = billRepository.findAll();
        return billMapper.toDtoList(bills);
    }

    //Create bill after the appointment is made
    public Bill createBill(CreateBillRequest request) {
        //Call appointment service to get its id and the patient's:
        AppointmentDTO appointmentDTO =
                appointmentClient.getAppointmentById(request.getAppointmentId());
        //Get patient id:
        String patientId= appointmentDTO.getPatientId();
        //Get full name patient
        String fullName= patientClient.getPatientById(patientId).getName()+ " "
                +patientClient.getPatientById(patientId).getSurname();

        Bill bill = Bill.builder()
                .fullName(fullName)
                .totalAmount(appointmentDTO.getPrice())
                .currency("EUR")
                .paymentMethod(request.getPaymentMethod())
                .appointmentId(request.getAppointmentId())
                .status(PaymentStatus.PAYMENT_PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        //Call StripeService to update payment in case it was successfull or not
        paymentService.processPayment(bill);

        return billRepository.save(bill);
    }

    //Make payment then update bill to a new status
    public void updateBill(PaymentIntent paymentIntent) {

        String paymentIntentId = paymentIntent.getId();

        Bill bill = billRepository
                .findByStripePaymentIntentId(paymentIntentId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        bill.setStatus(PaymentStatus.PAYED);
        bill.setPaidAt(LocalDateTime.now());

        billRepository.save(bill);
    }




}
