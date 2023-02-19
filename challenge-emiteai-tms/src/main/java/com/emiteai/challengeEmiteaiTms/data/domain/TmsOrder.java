package com.emiteai.challengeEmiteaiTms.data.domain;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;

@Entity
@Table(name = "tms_order")
public class TmsOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", columnDefinition = "jsonb", nullable = false)
    private String orderId;

    @Column(name = "status", nullable = false)
    private String status;
}
