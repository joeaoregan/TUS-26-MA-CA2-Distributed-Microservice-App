package com.tus.guitarorders.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tus.guitarorders.constants.GuitarOrdersConstants;
import com.tus.guitarorders.dto.CustomerDto;
import com.tus.guitarorders.dto.ResponseDto;
import com.tus.guitarorders.service.IGuitarOrdersService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class GuitarOrdersController {

    IGuitarOrdersService iGuitarOrdersService;

    @GetMapping("/sayHello")
    public String sayHello() {
        return "Hello World";
    }

    @PostMapping()
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
        iGuitarOrdersService.createOrder(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(GuitarOrdersConstants.STATUS_201, GuitarOrdersConstants.MESSAGE_201));
    }
}
