package doctor.app.billing_service.feign;

import doctor.app.billing_service.dtos.PatientDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "patient-service")
public interface PatientClient {
    @GetMapping("/{patientId}")
    PatientDTO getPatientById(@PathVariable String patientId);
}
