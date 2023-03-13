package com.example.backend_server.controller;

import com.example.backend_server.model.Post;
import com.example.backend_server.model.User;
import com.example.backend_server.service.PostService;
import com.example.backend_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllPosts() {
        List<Post> allPosts = postService.getAll();
        List<PostRecord> postRecords = allPosts.stream().map(post -> new PostRecord(post.getId(), post.getContent())).toList();
        return ResponseEntity.ok(postRecords);
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyPosts(Principal principal){
        User loggedUser = (User) userService.loadUserByUsername(principal.getName());
        List<Post> postsByUser = postService.getPostsByUser(loggedUser);
        List<PostRecord> postRecords = postsByUser.stream().map(post -> new PostRecord(post.getId(), post.getContent())).toList();
        return ResponseEntity.ok(postRecords);
    }


    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody PostRecord postRecord, Principal principal){
        User loggedUser = (User) userService.loadUserByUsername(principal.getName());
        Post post = new Post(postRecord.content);
        post.setUser(loggedUser);
        Post saved = postService.save(post);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PostRecord(saved.getId(), saved.getContent()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePost(@RequestBody PostRecord postRecord){
        postService.delete(postRecord.id);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/update")
    public ResponseEntity<?> createPost(@RequestBody PostRecord postRecord){
        Post post = postService.findById(postRecord.id);
        post.setContent(postRecord.content);

        Post saved = postService.save(post);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PostRecord(saved.getId(), saved.getContent()));
    }

    public record PostRecord(Long id, String content) {
    }
}
