package com.tus.guitarinventory.dto;

import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "inventory")
@Getter
@Setter
public class InventoryContactInfoDto { // Lab 19: Change to normal class from record
    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}