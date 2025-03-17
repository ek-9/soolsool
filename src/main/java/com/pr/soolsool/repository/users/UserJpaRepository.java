package com.pr.soolsool.repository.users;

import com.pr.soolsool.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findById(Long userId);
    Optional<Users> findByUsername(String name);
}
