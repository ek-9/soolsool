package com.pr.soolsool.controller;


import com.pr.soolsool.domain.Board;
import com.pr.soolsool.domain.Users;
import com.pr.soolsool.dto.BoardDTO;
import com.pr.soolsool.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/createBoard")
    public String createBoardForm() {
        return "reviews/createReviewForm";
    }

    @PostMapping("/createBoard")
    public String createBoard(@ModelAttribute("request") BoardDTO boardDTO) {
        log.info("postmapping create board form");
        for (int i = 0; i < 100; i++) {
            boardService.save(boardDTO);
        }
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String list(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        log.info("getMapping list");
        // 서비스에서 페이징 처리된 데이터 가져오기
        Page<Board> paging = boardService.getBoards(pageable);

        model.addAttribute("paging", paging);
        log.info("getMapping List fin");
        return "reviews/review"; // Thymeleaf 템플릿 파일 이름
    }

    @GetMapping("/boards/{id}")
    public String readReview(@PathVariable("id") Long id, Model model) {
        Users findUser = boardService.checkUser();
        Board findBoard = boardService.readReview(id);
        boolean isAuthor = (findUser.getId().equals(findBoard.getAuthor().getId()));

        model.addAttribute("isAuthor", isAuthor);
        model.addAttribute("board",findBoard);
        return "reviews/reviewDetails";
    }

    @GetMapping("boards/{id}/edit")
    public String updateReview(@PathVariable("id") Long id, Model model) {
        Board board = boardService.readReview(id);
        model.addAttribute("board", board);
        return "reviews/updateReviewForm";
    }

    @PostMapping("boards/{id}/edit")
    public String update(@PathVariable("id") Long id, @ModelAttribute("board") BoardDTO boardDTO){
        boardService.update(boardDTO, id);
        return "redirect:/boards/"+id;
    }

    @GetMapping("/list/delete/{id}")
    public String deleteReview(@PathVariable("id") Long id) {
        boardService.deleteReivew(id);
        return "redirect:/list";
    }


}
