package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommontResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentFeginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderFeignController {
    @Resource
    private PaymentFeginService paymentFeginService;

    @GetMapping("consumer//payment/get/{id}")
    public CommontResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return paymentFeginService.getPaymentById(id);
    };

    @GetMapping("/consumer/payment/feign/timeout")
    public String paymentFeignTimeOut(){
        return paymentFeginService.paymentFeignTimeOut();
    }
}
