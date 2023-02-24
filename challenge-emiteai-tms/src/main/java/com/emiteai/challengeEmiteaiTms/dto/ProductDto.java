package com.emiteai.challengeEmiteaiTms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class ProductDto {

    @JsonProperty("product_id")
    long productId;
    int quantity;
}
