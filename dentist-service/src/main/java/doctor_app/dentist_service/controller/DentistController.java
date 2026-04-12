package doctor_app.dentist_service.controller;

import doctor_app.dentist_service.dtos.DentistAddRequest;
import doctor_app.dentist_service.dtos.DentistResponse;
import doctor_app.dentist_service.mapper.DentistMapper;
import doctor_app.dentist_service.models.Dentist;
import doctor_app.dentist_service.service.DentistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dentists")
@RequiredArgsConstructor
public class DentistController {

    private final DentistService dentistService;
    private final DentistMapper dentistMapper;

    @GetMapping("/{doctorId}")
    public ResponseEntity<DentistResponse> getDentistById(@PathVariable String doctorId) {
        DentistResponse dentist= dentistService.getById(doctorId);
        return ResponseEntity.ok(dentist);
    }

    @GetMapping
    public ResponseEntity<List<DentistResponse>> getAllDentists() {
        List<DentistResponse> dentists= dentistService.getAll();
        return ResponseEntity.ok(dentistService.getAll());
    }

    @PostMapping
    public ResponseEntity<Dentist> createDentist(@Valid @RequestBody DentistAddRequest dto) {
        Dentist saved = dentistService.saveDentist(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> deleteDentist(@PathVariable String doctorId) {
        dentistService.deleteDentist(doctorId);
        return ResponseEntity.noContent().build();
    }
}
