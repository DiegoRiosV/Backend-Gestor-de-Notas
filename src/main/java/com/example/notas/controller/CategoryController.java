package com.example.notas.controller;

import com.example.notas.model.Category;
import com.example.notas.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
@Tag(name = "Gestión de Categorías", description = "Endpoints para crear, obtener y eliminar categorías.")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // GET /api/categories -> Get all categories
    @Operation(summary = "Obtener todas las categorías", description = "Devuelve una lista de todas las categorías existentes.")
    @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida exitosamente.")
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // POST /api/categories -> Create a new category
    @Operation(summary = "Crear una nueva categoría", description = "Crea una nueva categoría con un nombre (ID) y color. El nombre debe ser único.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente."),
        @ApiResponse(responseCode = "400", description = "Petición inválida (ej. nombre vacío o duplicado).")
    })
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            Category savedCategory = categoryService.createCategory(category);
            return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(summary = "Eliminar una categoría", description = "Elimina una categoría por su ID (nombre).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría eliminada exitosamente."),
        @ApiResponse(responseCode = "404", description = "La categoría no fue encontrada."),
        @ApiResponse(responseCode = "409", description = "No se puede eliminar la categoría porque está en uso.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {
            categoryService.deleteCategory(id);
            response.put("success", true);
            response.put("message", "Category deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {

            response.put("success", false);
            response.put("message", "Could not delete category. It might be in use by one or more notes.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}
