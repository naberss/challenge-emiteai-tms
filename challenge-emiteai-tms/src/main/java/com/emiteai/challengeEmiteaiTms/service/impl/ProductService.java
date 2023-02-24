package com.emiteai.challengeEmiteaiTms.service.impl;

import com.emiteai.challengeEmiteaiTms.data.domain.Product;
import com.emiteai.challengeEmiteaiTms.data.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService  {

    @Autowired
    private ProductRepository productRepository;

    public Product findById(Long id) /*throws ElementNotFoundException*/ {
        return productRepository.findById(id).orElse(null)/*orElseThrow(() -> new ElementNotFoundException("Product",id))*/;
    }

    public boolean existsById(Long id) {
        return productRepository.existsById(id);
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
