package com.example.backend_server.repository;

import com.example.backend_server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);
//    Boolean existsByUsername(String userName);
}
