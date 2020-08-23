package com.atguigu.springcloud;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


public class test {

    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void test(){
        String object = restTemplate.getForObject("http://api.51bccm.com/api_login?user=1072547941&password=lovepeng123", String.class);
        String token = object.substring(7);
        
    }

}
