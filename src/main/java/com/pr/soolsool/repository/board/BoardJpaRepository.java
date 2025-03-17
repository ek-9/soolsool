package com.pr.soolsool.repository.board;

import com.pr.soolsool.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BoardJpaRepository extends JpaRepository<Board, Long> {
    Page<Board> findAll(Pageable pageable); // 페이징 메서드

}
