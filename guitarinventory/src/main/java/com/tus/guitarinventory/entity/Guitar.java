package com.tus.guitarinventory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "guitar")
@Getter @Setter @ToString 
@AllArgsConstructor @NoArgsConstructor
public class Guitar extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guitarId;

    private String modelName;

    private String brand;

    private String serialNumber;

    private Integer price;

    private Integer totalStock;

    private Integer availableStock;
}