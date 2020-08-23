package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymetHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymetHystrixService paymetHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        return paymetHystrixService.paymentInfo_OK(id);
    };

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentInfo_NOKHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })
    public String paymentInfo_NOK(@PathVariable("id") Integer id){
        int a = 10 / 0;
        return paymetHystrixService.paymentInfo_NOK(id);
    }

    public String paymentInfo_NOKHandler(@PathVariable("id") Integer id){
        return "我是消费者80，调用错误，请稍后重试";
    }
    @GetMapping("/consumer/payment/hystrix/nok/{id}")
    @HystrixCommand
    public String paymentInfo_NOK1(@PathVariable("id") int id){
        int a = 10/0;
        return "这个是测试默认处理异常信息Global的方法";
    }

    //global fallback
    public String payment_Global_FallbackMethod(){
        return "Global异常处理信息，请稍后再试";
    }
}
