package com.xxx.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public Queue dynamicQueue() {
        return new Queue("dynamic", false, false, false);
    }

}