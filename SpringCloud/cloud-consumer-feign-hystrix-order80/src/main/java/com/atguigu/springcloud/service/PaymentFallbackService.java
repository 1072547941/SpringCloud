package com.atguigu.springcloud.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentFallbackService implements PaymetHystrixService{
    @Override
    public String paymentInfo_OK(Integer id) {
        return "--------PaymentFallbackService fall paymentInfo_OK,**********";
    }

    @Override
    public String paymentInfo_NOK(Integer id) {
        return "--------PaymentFallbackService fall paymentInfo_NOK,----------------";
    }
}
