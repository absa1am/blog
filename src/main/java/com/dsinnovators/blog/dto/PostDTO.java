package com.dsinnovators.blog.dto;

import com.dsinnovators.blog.models.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class PostDTO {

    @NotBlank(message = "Title can not be empty")
    private String title;
    @NotBlank(message = "Description can not be empty")
    private String description;
    private MultipartFile image;
    @NotNull(message = "Category can not be empty")
    private Category category;

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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
