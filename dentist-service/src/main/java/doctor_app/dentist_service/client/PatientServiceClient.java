package doctor_app.dentist_service.client;

import doctor_app.dentist_service.dtos.PatientSimple;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "patient-service", url = "http://patient-service:8081")
public interface PatientServiceClient {

    @GetMapping("/patients")
    List<PatientSimple> getPatientsByDoctorId(@RequestParam("doctorId") String doctorId);
}
