package com.pr.soolsool.service;

import com.pr.soolsool.domain.Users;
import com.pr.soolsool.dto.UserDTO;
import com.pr.soolsool.repository.users.UserJpaRepository;
import com.pr.soolsool.repository.users.updateUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserJpaRepository jpaRepository;
    private final PasswordEncoder passwordEncoder;

    public Users save(UserDTO userDto) {
        String r = "USER";
        if (userDto.getUsername().equals("admin")) {
            r = "ADMIN";
        }
//        Users buildUser = Users.userJoinDTOBuilder()
//                .username(userDto.getUsername())
//                .email(userDto.getEmail())
//                .password(passwordEncoder.encode(userDto.getPassword()))
//                .userType(UserType.INDIVISUAL)
//                .userRole(r)
//                .build();

        Users buildUser = Users.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .userRole(r)
                .userType(userDto.getUserType())
                .gender(userDto.getGender())
                .selfIntroduce(userDto.getSelfIntroduce())
                .followings(0)
                .follows(0)
                .number(userDto.getNumber())
                .build();


        jpaRepository.save(buildUser);
        return buildUser;
    }

    public void updateUser(Long userId, updateUserDTO updateUser) {
        Users findUser = jpaRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("잘못된 계정"));
        findUser.update(updateUser.getUsername(), updateUser.getPassword());
    }






}
