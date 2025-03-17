package com.pr.soolsool.service;

import com.pr.soolsool.domain.Board;
import com.pr.soolsool.domain.Users;
import com.pr.soolsool.dto.BoardDTO;
import com.pr.soolsool.repository.board.BoardJpaRepository;
import com.pr.soolsool.repository.users.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardJpaRepository jpaRepository;
    private final UserJpaRepository userRepository;

    public Board save(BoardDTO boardDTO) {
        Users users = checkUser();
        LocalDateTime time = LocalDateTime.now();
        log.info("board save username = {}",users.getId());

        Board createBoard = Board.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .author(users)
                .createdAt(time)
                .build();

        jpaRepository.save(createBoard);
        return createBoard;
    }

    public void update(BoardDTO boardDTO, Long id) {
        LocalDateTime time = LocalDateTime.now();

        Board updateBoard = jpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        updateBoard.edit(boardDTO.getTitle(), boardDTO.getContent(), time);

        log.info("111");
        jpaRepository.save(updateBoard);
    }

    public void deleteReivew(Long id) {
        jpaRepository.deleteById(id);
    }


    public Page<Board> getBoards(Pageable pageable) {
//        Page<Board> boards = jpaRepository.findAll(pageable);
        return jpaRepository.findAll(pageable);
    }

    public Users checkUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new IllegalStateException("로그인된 사용자가 없습니다.");
        }

        String name = authentication.getName();
        return userRepository.findByUsername(name)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + name));
    }

    public Board readReview(Long id) {
        Board findBoard = jpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));;
        findBoard.viewIncrease();
        jpaRepository.save(findBoard);
        return findBoard;
    }
}
