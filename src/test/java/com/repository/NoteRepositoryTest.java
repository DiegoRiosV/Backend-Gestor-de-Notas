package com.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.notas.model.Note;
import com.example.notas.repository.NoteRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest 
class NoteRepositoryTest {

    @Autowired 
    private NoteRepository noteRepository;


    @Test
    void save_shouldSaveNoteAndReturnIt() {
        // Arrange
        Note note = new Note("Content");
        note.setIdNote("abcde12345"); 

        // Act
        Note saved = noteRepository.save(note);

        // Assert
        assertNotNull(saved);
        assertEquals("abcde12345", saved.getIdNote());
    }

    @Test
    void findById_shouldReturnNoteIfExists() {
        // Arrange
        Note note = new Note("Content");
        note.setIdNote("testId1234");
        noteRepository.save(note); 

        // Act
        Optional<Note> found = noteRepository.findById("testId1234");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("testId1234", found.get().getIdNote());
    }
}