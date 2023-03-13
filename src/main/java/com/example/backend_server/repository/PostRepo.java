package com.example.backend_server.repository;

import com.example.backend_server.model.Post;
import com.example.backend_server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findAllByUser(User user);

}
