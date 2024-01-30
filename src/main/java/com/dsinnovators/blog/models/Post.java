package com.dsinnovators.blog.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posts_id_seq")
    @SequenceGenerator(name = "posts_id_seq", sequenceName = "posts_id_seq", allocationSize = 1)
    private Long id;
    @NotBlank(message = "Title can not be empty")
    private String title;
    @NotBlank(message = "Description can not be empty")
    private String description;
    private String image;
    @ManyToOne
    private User user;
    @ManyToOne
    @NotNull(message = "Category can not be empty")
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUser(User user) { this.user = user; }

    public User getUser() { return user; }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
