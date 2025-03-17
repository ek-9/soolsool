package com.pr.soolsool.service;

import com.pr.soolsool.domain.User_Role;
import com.pr.soolsool.domain.Users;
import com.pr.soolsool.repository.users.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

//    private final UserRepositoryImpl userRepository;
    private final UserJpaRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> u = userRepository.findByEmail(username);
        if (u.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }

        Users user = u.get();
        List<GrantedAuthority> authorities = new ArrayList<>();


        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(User_Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(User_Role.USER.getValue()));
        }
        log.info(user.getUserRole());
        log.info(user.getEmail());
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getUserRole())
                .build();
//        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
