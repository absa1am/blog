package com.dsinnovators.blog.repositories;

import com.dsinnovators.blog.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByIsDeletedIsFalse();
    Optional<Category> findByIdAndIsDeletedIsFalse(Long id);
    Page<Category> findAllByIsDeletedIsFalse(Pageable pageable);

}
