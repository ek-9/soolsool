package com.pr.soolsool.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "viewCount")
    private Integer viewCount;


//    @JoinColumn(name = "user_id", nullable = false) // "user_id" 외래 키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users author;

    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updateAt")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder
    public Board(String title, String content, Users author, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.viewCount = 0;
    }

    public void edit(String title, String content, LocalDateTime time) {
        this.title = title;
        this.content = content;
        this.createdAt = time;
    }
    public void viewIncrease() {
        this.viewCount++;
    }
}
