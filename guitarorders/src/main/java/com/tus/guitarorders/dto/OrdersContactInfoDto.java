package com.tus.guitarorders.dto;

import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "orders")
@Getter
@Setter
public class OrdersContactInfoDto { // Lab 19: Change to normal class from record
    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}
