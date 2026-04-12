package doctor.app.billing_service.feign;

import doctor.app.billing_service.dtos.AppointmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "appointment-service")
public interface AppointmentClient {
    @GetMapping("/{appointmentId}")
    AppointmentDTO getAppointmentById(@PathVariable String appointmentId);
}
