package com.emiteai.challengeEmiteaiTms.data.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Table(name = "tms_order")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)})
public class TmsOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Type(type = "jsonb")
    @Column(name = "order_id", columnDefinition = "json", nullable = true)
    private String orderId;

    @Column(name = "status", nullable = false)
    private String status;
}
