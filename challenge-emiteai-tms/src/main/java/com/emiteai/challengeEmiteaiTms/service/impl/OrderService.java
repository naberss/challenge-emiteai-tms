package com.emiteai.challengeEmiteaiTms.service.impl;

import com.emiteai.challengeEmiteaiTms.data.domain.Order;
import com.emiteai.challengeEmiteaiTms.data.domain.Product;
import com.emiteai.challengeEmiteaiTms.data.repositories.OrderRepository;
import com.emiteai.challengeEmiteaiTms.data.repositories.ProductRepository;
import com.emiteai.challengeEmiteaiTms.exception.ElementNotFoundException;
import com.emiteai.challengeEmiteaiTms.service.GenericDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements GenericDaoService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order findById(Long id) throws ElementNotFoundException {
        return orderRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Order",id));
    }

    public List<Order> findAll() throws RuntimeException {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new RuntimeException("No products found");
        }
        return orders;
    }
}
