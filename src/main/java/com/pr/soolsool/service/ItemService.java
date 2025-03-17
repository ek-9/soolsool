package com.pr.soolsool.service;

import com.pr.soolsool.domain.Item;
import com.pr.soolsool.domain.Users;
import com.pr.soolsool.dto.ItemDTO;
import com.pr.soolsool.repository.item.ItemJpaRepository;
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

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemService {

    private final ItemJpaRepository itemJpaRepository;
    private final UserJpaRepository userRepository;

    public Item save(ItemDTO itemDTO) {
        Users users = checkUser();
        LocalDateTime time = LocalDateTime.now();

        Item createItem = Item.builder()
                .productName(itemDTO.getProductName())
                .content(itemDTO.getContent())
                .createdAt(time)
                .quantity(itemDTO.getQuantity())
                .likes(0)
                .corporateMemberId(users)
                .price(itemDTO.getPrice())
                .build();
        itemJpaRepository.save(createItem);
        return createItem;
    }

    public void update(ItemDTO itemDTO, Long id) {
        LocalDateTime time = LocalDateTime.now();

        Item updateItem = itemJpaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이템 없음"));
        updateItem.edit(itemDTO.getProductName(), itemDTO.getContent(), itemDTO.getPrice(), time);
    }

    public Page<Item> getItems(Pageable pageable) {
        return itemJpaRepository.findAll(pageable);
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


    public Item readItem(Long id) {
        return itemJpaRepository.findById(id).
                orElseThrow(
                        () -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + id)
                );
    }
}
