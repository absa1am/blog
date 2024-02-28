package com.dsinnovators.blog.repositories;

import com.dsinnovators.blog.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByIsDeletedIsFalse();
    Optional<Post> findByIdAndIsDeletedIsFalse(Long id);
    Page<Post> findAllByIsDeletedIsFalse(Pageable pageable);

}
