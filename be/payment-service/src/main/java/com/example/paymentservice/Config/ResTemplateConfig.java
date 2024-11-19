package com.example.paymentservice.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ResTemplateConfig {
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate=new RestTemplate();
        return new RestTemplate();
    }
}

