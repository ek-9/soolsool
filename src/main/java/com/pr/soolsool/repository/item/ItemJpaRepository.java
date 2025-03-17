package com.pr.soolsool.repository.item;

import com.pr.soolsool.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {
}
