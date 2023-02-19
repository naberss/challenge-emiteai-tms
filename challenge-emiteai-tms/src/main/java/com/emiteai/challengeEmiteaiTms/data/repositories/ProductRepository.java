package com.emiteai.challengeEmiteaiTms.data.repositories;

import com.emiteai.challengeEmiteaiTms.data.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
