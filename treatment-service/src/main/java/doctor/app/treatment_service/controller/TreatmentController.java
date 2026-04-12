package doctor.app.treatment_service.controller;

import doctor.app.treatment_service.models.Treatment;
import doctor.app.treatment_service.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/treatments")
public class TreatmentController {
    @Autowired
    private TreatmentService treatmentService;

    @GetMapping("/all")
    public List<Treatment> getAllTreatment(){
        return treatmentService.getAllTreatments();
    }

    @GetMapping("/id/{id}")
    public Treatment getTreatmentById(@PathVariable String id){
        return treatmentService.getTreatmentById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTreatment(@PathVariable String id) {
        treatmentService.deleteTreatment(id);
    }

    @PostMapping("/create")
    public Treatment createTreatment(@RequestBody Treatment treatment){
        return treatmentService.createTreatment(treatment);
    }

}
