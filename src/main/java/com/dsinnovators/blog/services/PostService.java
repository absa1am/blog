package com.dsinnovators.blog.services;

import com.dsinnovators.blog.dto.PostDTO;
import com.dsinnovators.blog.models.Post;
import com.dsinnovators.blog.models.User;
import com.dsinnovators.blog.repositories.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@PropertySource("classpath:application.properties")
public class PostService {

    @Value("${spring.file.upload-location}")
    private String uploadLocation;
    private PostRepository postRepository;
    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post find(Long id) {
        return postRepository.findById(id).get();
    }

    public Post save(PostDTO postDTO, User user) {
        MultipartFile image = postDTO.getImage();
        String imageName = image.getOriginalFilename();

        if (image != null) {
            try {
                image.transferTo(new File(uploadLocation, imageName));
            } catch (IOException | RuntimeException e) {
                logger.error(e.getMessage());
            }
        }

        Post post = new Post();

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setImage(imageName);
        post.setUser(user);
        post.setCategory(postDTO.getCategory());

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

    public void delete(Long id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) {
            postRepository.delete(post.get());
        }
    }

}
