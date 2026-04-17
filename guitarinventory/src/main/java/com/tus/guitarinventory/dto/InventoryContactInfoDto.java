package com.tus.guitarinventory.dto;

import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "inventory")
public record InventoryContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) {

}