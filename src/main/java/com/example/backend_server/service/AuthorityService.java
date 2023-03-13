package com.example.backend_server.service;

import com.example.backend_server.model.Authority;
import com.example.backend_server.repository.AuthorityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorityService {
    private final AuthorityRepo authorityRepo;

    public Authority findByName(String name){
        return authorityRepo.findAuthorityByRoleName(name);
    }
}
