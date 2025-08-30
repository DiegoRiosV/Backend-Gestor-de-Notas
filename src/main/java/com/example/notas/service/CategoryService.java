package com.example.notas.service;

import com.example.notas.model.Category;
import com.example.notas.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        if (category.getCategoryId() == null || category.getCategoryId().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name (ID) cannot be empty.");
        }

        if (categoryRepository.existsById(category.getCategoryId())) {
            throw new IllegalArgumentException("Category with name '" + category.getCategoryId() + "' already exists.");
        }
        return categoryRepository.save(category);
    }

    public void deleteCategory(String categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Category not found with id: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }
}
