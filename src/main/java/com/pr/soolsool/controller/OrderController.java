package com.pr.soolsool.controller;

import com.pr.soolsool.domain.Item;
import com.pr.soolsool.domain.Users;
import com.pr.soolsool.dto.OrderDTO;
import com.pr.soolsool.service.ItemService;
import com.pr.soolsool.service.OrderService;
import com.pr.soolsool.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final ItemService itemService;

    @GetMapping("/orderDetails/{itemId}")
    public String orderForm(
            @PathVariable("itemId") Long id, Model model
    ) {
        Users findUser = itemService.checkUser();
        Item findItem = itemService.readItem(id);

        model.addAttribute("user", findUser);
        model.addAttribute("item", findItem);

        return "order/orderDetails";
    }

    @PostMapping("/order")
    public String order(
            @ModelAttribute("request") OrderDTO orderDTO
    ) {
        log.info("post order");
        orderService.orderItem(orderDTO.getItemId(), orderDTO.getQuantity());
        log.info("quantity : {}",orderDTO.getQuantity());
        log.info("post order fin");
        return "redirect:/itemList";
    }
}
