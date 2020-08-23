package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommontResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    public CommontResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("插入结果:"+result);
        if (result>0){
            return new CommontResult(200,"插入数据库成功,serverProt:"+serverPort,result);
        }else {
            return new CommontResult(444,"插入数据库失败",null);
        }
    }
    @GetMapping("/payment/get/{id}")
    public CommontResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("查询的结果是："+payment);
        if (payment!=null){
            return new CommontResult(200,"查询数据成功,serverProt:"+serverPort,payment);
        }else {
            return new CommontResult(444,"没有对应数据，查询的id"+id,null);
        }
    }


    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }
}


