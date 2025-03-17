package com.pr.soolsool.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Users implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "gender")
    private Gender gender;

    @Column(name="user_role")
    private String userRole;

    private UserType userType;

    private Integer balance = 100000;

    private Integer follows; // 팔로우 명단
    private Integer followings; // 팔로잉 명단

    private String number; // 핸드폰 전화번호
    private String selfIntroduce; // 한줄 자기소개

//    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "author")
    private List<Board> boards = new ArrayList<>();

    @Override //권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Builder
    public Users(String username, String password, String email, String userRole, UserType userType, Integer follows, Integer followings, String number, String selfIntroduce, Gender gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
        this.userType = userType;
        this.follows = 0;
        this.followings = 0;
        this.number = number;
        this.selfIntroduce = selfIntroduce;
        this.gender = gender;
    }

    public void update(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
