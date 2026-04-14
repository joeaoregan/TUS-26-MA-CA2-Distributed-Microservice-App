package com.tus.guitarorders.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GuitarOrders extends BaseEntity {

    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "guitar_id")
    private Long guitarId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "status")
    private String status;
}
