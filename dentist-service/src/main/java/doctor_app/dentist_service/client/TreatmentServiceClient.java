package doctor_app.dentist_service.client;

import doctor_app.dentist_service.dtos.TreatmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "treatment-service", url = "http://treatment-service:8083")
public interface TreatmentServiceClient {

    @GetMapping("/treatments")
    List<TreatmentResponse> getTreatmentsByPatientId(@RequestParam("patientId") String patientId);
}
