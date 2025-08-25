package com.examly.springapp.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.examly.springapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    List<User> findByRoleIn(List<String> roles);

    default Optional<User> findByUsernameOrEmail(String input) {
        return findByUsername(input).or(() -> findByEmail(input));
    }

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
