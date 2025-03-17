package com.pr.soolsool.controller;

import com.pr.soolsool.domain.Board;
import com.pr.soolsool.domain.Item;
import com.pr.soolsool.domain.UserType;
import com.pr.soolsool.domain.Users;
import com.pr.soolsool.dto.BoardDTO;
import com.pr.soolsool.dto.ItemDTO;
import com.pr.soolsool.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/itemList")
    public String list(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Users findUser = itemService.checkUser();
        boolean isCorporation = (findUser.getUserType() == UserType.CORPERATE);
        log.info("iscorporation = {}", isCorporation);


        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        log.info("getMapping list");
        // 서비스에서 페이징 처리된 데이터 가져오기
        Page<Item> paging = itemService.getItems(pageable);

        model.addAttribute("isCorporation", isCorporation);
        model.addAttribute("paging", paging);
        log.info("getMapping List fin");
        return "items/item"; // Thymeleaf 템플릿 파일 이름
    }

    @GetMapping("/createItem")
    public String createForm() {
        return "items/createItemForm";
    }

    @PostMapping("/createItem")
    public String itemList(@ModelAttribute("request") ItemDTO itemDTO) {
        log.info("postmapping create item form");
        for (int i = 0; i < 100; i++) {
            itemService.save(itemDTO);
        }
        return "redirect:/itemList";
    }

    @GetMapping("/itemDetails/{id}")
    public String readItem(@PathVariable("id") Long id, Model model) {
        Item findItem = itemService.readItem(id);
        model.addAttribute("item",findItem);
        return "order/itemDetail";
    }

}
