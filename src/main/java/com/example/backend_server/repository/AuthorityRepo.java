package com.example.backend_server.repository;

import com.example.backend_server.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepo extends JpaRepository<Authority, Long> {
    Authority findAuthorityByRoleName(String name);
}
