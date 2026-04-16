// Lab 3
package com.tus.guitarinventory.service.impl;

import com.tus.guitarinventory.constants.InventoryConstants;
import com.tus.guitarinventory.dto.InventoryDto;
import com.tus.guitarinventory.entity.Guitar;
import com.tus.guitarinventory.exception.ResourceNotFoundException;
import com.tus.guitarinventory.mapper.InventoryMapper;
import com.tus.guitarinventory.repository.GuitarRepository;
import com.tus.guitarinventory.service.IInventoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements IInventoryService {

    private GuitarRepository guitarRepository;

    @Override
    public void createGuitar(InventoryDto inventoryDto) {
        Guitar guitar = InventoryMapper.mapToGuitar(inventoryDto, new Guitar());
        guitarRepository.save(guitar);
    }

    @Override
    public InventoryDto fetchGuitar(String serialNumber) {
        Guitar guitar = guitarRepository.findBySerialNumber(serialNumber).orElseThrow(
                () -> new ResourceNotFoundException("Guitar", "serialNumber", serialNumber)
        );
        return InventoryMapper.mapToInventoryDto(guitar, new InventoryDto());
    }

    @Override
    public boolean updateGuitar(InventoryDto inventoryDto) {
        Guitar guitar = guitarRepository.findBySerialNumber(inventoryDto.getSerialNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Guitar", "serialNumber", inventoryDto.getSerialNumber())
        );
        InventoryMapper.mapToGuitar(inventoryDto, guitar);
        guitarRepository.save(guitar);
        return true;
    }

    @Override
    public boolean deleteGuitar(String serialNumber) {
        Guitar guitar = guitarRepository.findBySerialNumber(serialNumber).orElseThrow(
                () -> new ResourceNotFoundException("Guitar", "serialNumber", serialNumber)
        );
        guitarRepository.delete(guitar);
        return true;
    }
}