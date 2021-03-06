package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommontResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

//    private static final String PAYMENT_URL = "http://localhost:8001";
    private static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalancer loadBalancer;
    @Resource
    private DiscoveryClient discoveryClient;


    @GetMapping("/consumer/payment/create")
    public CommontResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommontResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommontResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommontResult.class);
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommontResult<Payment> getPayment2(@PathVariable("id") Long id){
        ResponseEntity<CommontResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommontResult.class);
        if (entity.getStatusCode().is2xxSuccessful()){
            //如果成功
            return entity.getBody();
        }else {
            return new CommontResult<>(444,"操作失败");
        }
    }

    @GetMapping("/consumer/payment/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances==null||instances.size()<=0) return null;
        ServiceInstance instance = loadBalancer.instances(instances);
        URI uri = instance.getUri();
        String object = restTemplate.getForObject(uri + "/payment/lb", String.class);
        return object;
    }

}
