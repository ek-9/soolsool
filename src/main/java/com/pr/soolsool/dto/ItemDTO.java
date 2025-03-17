package com.pr.soolsool.dto;

import com.pr.soolsool.domain.Users;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemDTO {

    private String productName;
    private String content;
    private Integer price;
    private Integer quantity;
    private Users user;
}
