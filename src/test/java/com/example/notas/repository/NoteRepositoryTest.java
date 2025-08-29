package com.example.notas.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.notas.model.Category;
import com.example.notas.model.Note;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = { "spring.jpa.hibernate.ddl-auto=create-drop" })
@EnableJpaRepositories(basePackages = "com.example.notas.repository") 
@EntityScan(basePackages = "com.example.notas.model")
class NoteRepositoryTest {

    @Autowired 
    private NoteRepository noteRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    void save_shouldSaveNoteAndReturnIt() {
        Category category = new Category();
        category.setCategoryId("test-cat");
        category.setColor("blue");
        categoryRepository.save(category);
        // Arrange
        Note note = new Note("Content");
        note.setIdNote("abcde12345"); 
        note.setCategory(category);

        // Act
        Note saved = noteRepository.save(note);

        // Assert
        assertNotNull(saved);
        assertEquals("abcde12345", saved.getIdNote());
    }

    @Test
    void findById_shouldReturnNoteIfExists() {
        Category category = new Category();
        category.setCategoryId("another-cat");
        category.setColor("red");
        categoryRepository.save(category);
        // Arrange
        Note note = new Note("Content");
        note.setIdNote("testId1234");
        note.setCategory(category);
        noteRepository.save(note); 

        // Act
        Optional<Note> found = noteRepository.findById("testId1234");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("testId1234", found.get().getIdNote());
    }
}