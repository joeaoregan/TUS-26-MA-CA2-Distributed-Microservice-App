package com.tus.guitarorders.dto;

import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "orders")
public record OrdersContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) {

}