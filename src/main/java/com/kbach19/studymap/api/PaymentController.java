package com.kbach19.studymap.api;

import com.kbach19.studymap.api.dto.BuyRequest;
import com.kbach19.studymap.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Void> buy(@RequestBody BuyRequest buyRequest) {
        paymentService.buy(buyRequest);
        return ResponseEntity.ok(null);
    }

}
