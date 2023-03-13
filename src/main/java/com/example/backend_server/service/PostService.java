package com.example.backend_server.service;

import com.example.backend_server.model.Post;
import com.example.backend_server.model.User;
import com.example.backend_server.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo postRepo;

    public Post save(Post post){
        return postRepo.save(post);
    }

    public void delete(Long id){
        postRepo.deleteById(id);
    }

    public List<Post> getAll(){
        return postRepo.findAll();
    }

    public Post findById(Long id) { return postRepo.findById(id).orElseThrow(() -> new RuntimeException("No post with such id..."));}

    public List<Post> getPostsByUser(User user){
        return postRepo.findAllByUser(user);
    }

}
