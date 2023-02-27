package com.emiteai.challengeEmiteaiTms.data.repositories;

import com.emiteai.challengeEmiteaiTms.data.domain.TmsOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TmsOrderRepository extends JpaRepository<TmsOrder, Long> {
    @Modifying
    @Query("UPDATE TmsOrder e SET e.status = :status WHERE e.id IN :id")
    int updateTmsOrderStatusById(@Param("status") String status, @Param("id") Long id);
}
