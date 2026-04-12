package doctor.app.billing_service.controller;

import doctor.app.billing_service.dtos.BillResponse;
import doctor.app.billing_service.dtos.CreateBillRequest;
import doctor.app.billing_service.models.Bill;
import doctor.app.billing_service.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    @GetMapping("id/{id}")
    public ResponseEntity<BillResponse> getBillById(@PathVariable String id){
        BillResponse bill = billService.getBillById(id);
        return ResponseEntity.ok(bill);
    }

    @GetMapping
    public ResponseEntity<List<BillResponse>> getAllBills(){
        List<BillResponse> bills = billService.getAllBills();
        return ResponseEntity.ok(bills);
    }

    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody CreateBillRequest bill){
        Bill createdBill = billService.createBill(bill);
        return ResponseEntity.ok(createdBill);
    }




}
