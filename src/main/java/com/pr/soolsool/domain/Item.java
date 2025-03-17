package com.pr.soolsool.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

//    @Column(name = "image_url")
//    private String imageUrl;
    @Column(name = "productName")
    private String productName;
    @Column(name = "content")
    private String content;

    @Column(name = "price")
    private Integer price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "viewCount")
    private Integer viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users corporateMemberId; // FK to Member (Corporate only)

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updateAt")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder
    public Item(String imageUrl, String productName, String content, Integer price, Integer quantity, Integer likes, Users corporateMemberId, LocalDateTime createdAt) {
//        this.imageUrl = imageUrl;
        this.productName = productName;
        this.content = content;
        this.price = price;
        this.quantity = quantity;
        this.likes = likes;
        this.corporateMemberId = corporateMemberId;
        this.createdAt = createdAt;
    }

    public void edit(String productName, String content, Integer price, LocalDateTime time) {
        this.productName = productName;
        this.content = content;
        this.price = price;
        this.createdAt = time;

    }

    public void viewIncrease() {
        this.viewCount++;
    }
}
