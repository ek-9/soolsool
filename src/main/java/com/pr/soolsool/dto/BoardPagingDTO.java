package com.pr.soolsool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter @Setter
public class BoardPagingDTO {
    private Long id;
    private String title;
    private String content;
    private String username;
    private Integer viewCount;
    private LocalDateTime createdAt;

}
