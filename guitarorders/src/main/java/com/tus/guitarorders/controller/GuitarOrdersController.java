package com.tus.guitarorders.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tus.guitarorders.constants.GuitarOrdersConstants;
import com.tus.guitarorders.dto.CustomerDto;
import com.tus.guitarorders.dto.ResponseDto;
import com.tus.guitarorders.service.IGuitarOrdersService;

import jakarta.validation.Valid; // Lab 7
import jakarta.validation.constraints.Pattern; // Lab 7
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class GuitarOrdersController {

    IGuitarOrdersService iGuitarOrdersService;

    @GetMapping("/sayHello")
    public String sayHello() {
        return "Hello World";
    }

    @GetMapping()
    public ResponseEntity<CustomerDto> fetchOrderDetails(@RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) { // Lab 7
        CustomerDto customerDto = iGuitarOrdersService.fetchOrder(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PostMapping()
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) { // Lab 7
        iGuitarOrdersService.createOrder(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(GuitarOrdersConstants.STATUS_201, GuitarOrdersConstants.MESSAGE_201));
    }

    @PutMapping()
    public ResponseEntity<ResponseDto> updateOrderDetails(@Valid @RequestBody CustomerDto customerDto) { // Lab 7
        boolean isUpdated = iGuitarOrdersService.updateOrder(customerDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(GuitarOrdersConstants.STATUS_200, GuitarOrdersConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(GuitarOrdersConstants.STATUS_500, GuitarOrdersConstants.MESSAGE_500));
        }
    }

    @DeleteMapping()
    public ResponseEntity<ResponseDto> deleteOrderDetails(@RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) { // Lab 7
        boolean isDeleted = iGuitarOrdersService.deleteOrder(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(GuitarOrdersConstants.STATUS_200, GuitarOrdersConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(GuitarOrdersConstants.STATUS_500, GuitarOrdersConstants.MESSAGE_500));
        }
    }
}
