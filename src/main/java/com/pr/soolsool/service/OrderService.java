package com.pr.soolsool.service;

import com.pr.soolsool.domain.Item;
import com.pr.soolsool.domain.Orders;
import com.pr.soolsool.domain.OrderItem;
import com.pr.soolsool.domain.Users;
import com.pr.soolsool.repository.item.ItemJpaRepository;
import com.pr.soolsool.repository.order.OrderJpaRepository;
import com.pr.soolsool.repository.users.UserJpaRepository;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderJpaRepository orderJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ItemJpaRepository itemJpaRepository;

    public Long orderItem(Long itemId, int quantity) {
        Users users = checkUser();
        Item item = itemJpaRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("제품없음"));

        List<OrderItem> orderItemList = new ArrayList<>();

        OrderItem orderItem = OrderItem.builder()
                .users(users)
                .item(item)
                .quantity(quantity)
                .build();

        orderItemList.add(orderItem);
        int totalPrice = 0;

        for (OrderItem o : orderItemList) {
            int p = o.getItem().getPrice() * o.getQuantity();
            totalPrice += p;
        }

        if (users.getBalance() < totalPrice) {
            throw new IllegalStateException("잔액 부족으로 주문할 수 없습니다.");
        }
        users.setBalance(users.getBalance() - totalPrice);

        log.info("item quantity = {}", item.getQuantity());
        log.info("order item quantity = {}", orderItem.getQuantity());
        Integer q = item.getQuantity() - orderItem.getQuantity();

        Item editItem = Item.builder()
                .productName(item.getProductName())
                .content(item.getContent())
                .price(item.getPrice())
                .quantity(q)
                .likes(item.getLikes())
                .corporateMemberId(item.getCorporateMemberId())
                .createdAt(item.getCreatedAt())
                .build();

        Orders orders = Orders.builder()
                .user(users)
                .orderItems(orderItemList)
                .totalPrice(totalPrice)
                .build();

        orderJpaRepository.save(orders);
        itemJpaRepository.save(editItem);

        return orderItem.getId();
    }

    public Users checkUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new IllegalStateException("로그인된 사용자가 없습니다.");
        }

        String name = authentication.getName();
        return userJpaRepository.findByUsername(name)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + name));
    }
}
