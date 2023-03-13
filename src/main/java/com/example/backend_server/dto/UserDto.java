package com.example.backend_server.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Data
@Component
@Getter
public class UserDto {
    private String userName;
    private String password;
}
