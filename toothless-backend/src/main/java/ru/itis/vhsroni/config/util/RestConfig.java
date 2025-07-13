package ru.itis.vhsroni.config.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestConfig {

    @Bean
    public RestClient restClient(){
        return RestClient.create();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
