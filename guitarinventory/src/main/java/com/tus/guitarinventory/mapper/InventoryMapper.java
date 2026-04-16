// Lab 3
package com.tus.guitarinventory.mapper;

import com.tus.guitarinventory.dto.InventoryDto;
import com.tus.guitarinventory.entity.Guitar;

public class InventoryMapper {

    public static InventoryDto mapToInventoryDto(Guitar guitar, InventoryDto inventoryDto) {
        inventoryDto.setModelName(guitar.getModelName());
        inventoryDto.setBrand(guitar.getBrand());
        inventoryDto.setSerialNumber(guitar.getSerialNumber());
        inventoryDto.setPrice(guitar.getPrice());
        inventoryDto.setTotalStock(guitar.getTotalStock());
        inventoryDto.setAvailableStock(guitar.getAvailableStock());
        return inventoryDto;
    }

    public static Guitar mapToGuitar(InventoryDto inventoryDto, Guitar guitar) {
        guitar.setModelName(inventoryDto.getModelName());
        guitar.setBrand(inventoryDto.getBrand());
        guitar.setSerialNumber(inventoryDto.getSerialNumber());
        guitar.setPrice(inventoryDto.getPrice());
        guitar.setTotalStock(inventoryDto.getTotalStock());
        guitar.setAvailableStock(inventoryDto.getAvailableStock());
        return guitar;
    }
}