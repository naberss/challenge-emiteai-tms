package com.emiteai.challengeEmiteaiTms.data.repositories;

import com.emiteai.challengeEmiteaiTms.data.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
