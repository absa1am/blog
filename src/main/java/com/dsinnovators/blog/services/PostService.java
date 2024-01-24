package com.dsinnovators.blog.services;

import com.dsinnovators.blog.models.Post;
import com.dsinnovators.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post find(Long id) {
        return postRepository.findById(id).get();
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Post update(Post post, Long id) {
        Post oldPost = postRepository.findById(id).get();

        oldPost.setId(post.getId());
        oldPost.setTitle(post.getTitle());
        oldPost.setDescription(post.getDescription());
        oldPost.setCategory(post.getCategory());

        return postRepository.save(oldPost);
    }

}
