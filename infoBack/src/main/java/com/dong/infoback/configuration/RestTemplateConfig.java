package com.dong.infoback.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public  class RestTemplateConfig {
    @Bean
    @LoadBalanced  // 开启客户端负载均衡（默认轮询策略）
    public RestTemplate restTemplate(){
    return new RestTemplate();

    }}
