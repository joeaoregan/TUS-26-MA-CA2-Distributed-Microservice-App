package com.tus.guitarinventory.entity;

import jakarta.persistence.Column;
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
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Guitar extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "guitar_id")
    private Long guitarId;

	@Column(name = "model_name")
    private String modelName;

    @Column(name = "brand")
    private String brand;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "price")
    private Integer price;

    @Column(name = "total_stock")
    private Integer totalStock;

    @Column(name = "available_stock")
    private Integer availableStock;
}
