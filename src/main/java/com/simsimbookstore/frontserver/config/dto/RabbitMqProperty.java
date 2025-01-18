package com.simsimbookstore.frontserver.config.dto;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMqProperty {
    private String secretKey;
    private Integer port;
    private String virtualHost;
}
