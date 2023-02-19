package com.emiteai.challengeEmiteaiTms.data.domain;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", columnDefinition = "jsonb")
    private String productId;

    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private String status;

    // Constructor, getters and setters
}