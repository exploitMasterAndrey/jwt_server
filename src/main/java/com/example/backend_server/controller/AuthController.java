package com.example.backend_server.controller;

import com.example.backend_server.config.JWTUtil;
import com.example.backend_server.dto.UserDto;
import com.example.backend_server.model.Authority;
import com.example.backend_server.model.User;
import com.example.backend_server.service.AuthorityService;
import com.example.backend_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class AuthController {
    private final UserService userService;
    private final AuthorityService authorityService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUserName(),
                        userDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(user.getUsername());

        List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                user.getId(),
                user.getUsername(),
                authorities));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        if (userService.existsByUserName(userDto.getUserName())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        Authority authority = authorityService.findByName("USER");

        User user = new User(
                userDto.getUserName(),
                passwordEncoder.encode(userDto.getPassword()),
                List.of(authority)
        );
        userService.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

//    @GetMapping("/userinfo")
//    @CrossOrigin(origins = "http://localhost:8081")
//    public ResponseEntity<?> getUserInfo(Principal user) {
//        User userObj = (User) userService.loadUserByUsername(user.getName());
//
//        return ResponseEntity.ok(userObj);
//    }

    public record JwtResponse(String jwt, Long id, String username, List<String> authorities) {}
}
