package com.dsinnovators.blog.services;

import com.dsinnovators.blog.dto.PostDTO;
import com.dsinnovators.blog.models.Post;
import com.dsinnovators.blog.models.User;
import com.dsinnovators.blog.repositories.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@PropertySource("classpath:application.properties")
public class PostService {

    @Value("${spring.file.upload-location}")
    private String uploadLocation;
    private final PostRepository postRepository;
    private final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final int DEFAULT_PAGE_SIZE = 4;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        return postRepository.findAllByIsDeletedIsFalse();
    }

    public Page<Post> getPosts(int page) {
        return postRepository.findAllByIsDeletedIsFalse(PageRequest.of(page, DEFAULT_PAGE_SIZE));
    }

    public Optional<Post> getPost(Long id) {
        return postRepository.findByIdAndIsDeletedIsFalse(id);
    }

    public Post savePost(PostDTO postDTO, User user) {
        LocalDateTime currentTimestamp = LocalDateTime.now();
        MultipartFile image = postDTO.getImage();
        String imageName = generateRandomFileName();

        boolean isFileUploaded = uploadFormFile(image, imageName);

        Post post = new Post();

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setImage(isFileUploaded? imageName:"");
        post.setUser(user);
        post.setCategory(postDTO.getCategory());
        post.setCreatedAt(currentTimestamp);
        post.setUpdatedAt(currentTimestamp);
        post.setIsDeleted(false);

        return postRepository.save(post);
    }

    public Post updatePost(PostDTO postDTO, Long id) {
        Post oldPost = postRepository.findById(id).get();

        MultipartFile image = postDTO.getImage();
        String imageName = generateRandomFileName();

        boolean isFileUploaded = uploadFormFile(image, imageName);

        oldPost.setId(id);
        oldPost.setTitle(postDTO.getTitle());
        oldPost.setDescription(postDTO.getDescription());
        oldPost.setCategory(postDTO.getCategory());
        oldPost.setUpdatedAt(LocalDateTime.now());

        if (isFileUploaded) {
            oldPost.setImage(imageName);
        }

        return postRepository.save(oldPost);
    }

    public void deletePost(Long id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) {
            post.get().setUpdatedAt(LocalDateTime.now());
            post.get().setIsDeleted(true);
        }

        postRepository.save(post.get());
    }

    private String generateRandomFileName() {
        return LocalDateTime.now().toString() + "-" + UUID.randomUUID().toString();
    }

    private boolean uploadFormFile(MultipartFile image, String imageName) {
        if (!image.isEmpty() || image.getSize() != 0) {
            try {
                image.transferTo(new File(uploadLocation, imageName));

                return true;
            } catch (IOException | RuntimeException e) {
                logger.error(e.getMessage());
            }
        }

        return false;
    }

}
