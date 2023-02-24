package com.emiteai.challengeEmiteaiTms.controllers;

import com.emiteai.challengeEmiteaiTms.dto.OrderDto;
import com.emiteai.challengeEmiteaiTms.dto.ProcessResponseDto;
import com.emiteai.challengeEmiteaiTms.dto.ProductDto;
import com.emiteai.challengeEmiteaiTms.exception.FailedOrderException;
import com.emiteai.challengeEmiteaiTms.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessResponseDto>createOrder(@RequestBody @Valid @Size(min = 1, max = 3)
                                                         List<ProductDto> products) throws FailedOrderException {

        return createReponseEntity(orderService.doProcess(products));
    }

    private ResponseEntity<ProcessResponseDto> createReponseEntity(final ProcessResponseDto responseDto) {
    if (!responseDto.isSuccess()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(responseDto);
    }
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(responseDto);
  }

}