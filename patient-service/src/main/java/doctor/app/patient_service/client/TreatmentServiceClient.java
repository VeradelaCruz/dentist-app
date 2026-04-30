package doctor.app.patient_service.client;

import doctor.app.patient_service.dtos.TreatmentSimple;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "treatment-service", url = "http://treatment-service:8083")
public interface TreatmentServiceClient {

    @GetMapping("/treatments")
    List<TreatmentSimple> getTreatmentsByDentistId(@RequestParam("dentistId") String dentistId);
}
