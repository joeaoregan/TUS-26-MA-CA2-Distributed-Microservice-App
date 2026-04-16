package com.tus.guitarinventory.controller;

import com.tus.guitarinventory.constants.InventoryConstants;
import com.tus.guitarinventory.dto.InventoryDto;
import com.tus.guitarinventory.dto.ResponseDto;
import com.tus.guitarinventory.service.IInventoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/guitars", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class InventoryController {

    private IInventoryService iInventoryService;

    // Lab 3
    @PostMapping()
    public ResponseEntity<ResponseDto> createGuitar(@Valid @RequestBody InventoryDto inventoryDto) {
        iInventoryService.createGuitar(inventoryDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(InventoryConstants.STATUS_201, InventoryConstants.MESSAGE_201));
    }

    @GetMapping()
    public ResponseEntity<InventoryDto> fetchGuitarDetails(@RequestParam String serialNumber) {
        InventoryDto inventoryDto = iInventoryService.fetchGuitar(serialNumber);
        return ResponseEntity.status(HttpStatus.OK).body(inventoryDto);
    }
}