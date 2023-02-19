package com.emiteai.challengeEmiteaiTms.data.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(name = "unit_value", nullable = false)
    private BigDecimal unitValue;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;
}
