package com.emiteai.challengeEmiteaiTms.data.repositories;

import com.emiteai.challengeEmiteaiTms.data.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Query("UPDATE Order e SET e.status = :status WHERE e.id IN :id")
    int updateOrderStatusById(@Param("status") String status, @Param("id") Long id);

}
