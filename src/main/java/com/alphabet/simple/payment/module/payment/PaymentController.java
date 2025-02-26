package com.alphabet.simple.payment.module.payment;

import com.alphabet.simple.payment.annotations.Permission;
import com.alphabet.simple.payment.outgoing.rabbitmq.PaymentProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentProducer paymentProducer;

    @GetMapping("/send")
    @Permission("sendMessage")
    public ResponseEntity<String> sendMessage(@RequestParam String message){
        paymentProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent " + message);
    }
}
