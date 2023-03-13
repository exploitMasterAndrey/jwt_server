package com.example.backend_server;

import com.example.backend_server.model.Authority;
import com.example.backend_server.model.User;
import com.example.backend_server.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BackendServerApplication {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(BackendServerApplication.class, args);
    }

//    @PostConstruct
//    protected void init(){
//        User admin = new User(
//                "andrey",
//                passwordEncoder.encode("12345678"),
//                List.of(
//                        new Authority("ADMIN"),
//                        new Authority("USER")
//                ));
//        userService.save(admin);
//    }

}
