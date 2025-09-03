package com.example.notas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
@Schema(description = "Represents a category for organizing notes.")
public class Category {

    @Id
    @Column(name = "category_id", length = 64)
    @Schema(description = "Unique identifier and name for the category.", example = "trabajo")
    private String categoryId;

    @Column(name = "color")
    @Schema(description = "Color code for the category in HEX format.", example = "#9DC3E6")
    private String color;

    public Category() {
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
