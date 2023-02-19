package com.emiteai.challengeEmiteaiTms.data.repositories;

import com.emiteai.challengeEmiteaiTms.data.domain.TmsOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TmsOrderRepository extends JpaRepository<TmsOrder, Long> {
}
