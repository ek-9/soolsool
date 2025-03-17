package com.pr.soolsool.repository.order;

import com.pr.soolsool.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Orders, Long> {
}
