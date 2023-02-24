package com.emiteai.challengeEmiteaiTms.controllers;

import com.emiteai.challengeEmiteaiTms.dto.OrderDto;
import com.emiteai.challengeEmiteaiTms.dto.ProcessResponseDto;
import com.emiteai.challengeEmiteaiTms.exception.FailedOrderException;
import com.emiteai.challengeEmiteaiTms.service.impl.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
}