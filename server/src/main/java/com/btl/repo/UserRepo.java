package com.btl.repo;


import com.btl.entity.Role;
import com.btl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    void deleteUserByUsername(String username);

    Iterable<User> findByRoles(Role role);
}