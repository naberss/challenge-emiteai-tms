package com.emiteai.challengeEmiteaiTms.service.impl;

import com.emiteai.challengeEmiteaiTms.data.domain.Product;
import com.emiteai.challengeEmiteaiTms.data.repositories.ProductRepository;
import com.emiteai.challengeEmiteaiTms.exception.ElementNotFoundException;
import com.emiteai.challengeEmiteaiTms.service.GenericDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements GenericDaoService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product findById(Long id) throws ElementNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Product",id));
    }

    /*@Override*/
    public List<Product> findAll() throws RuntimeException {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new RuntimeException("No products found");
        }
        return products;
    }
}
