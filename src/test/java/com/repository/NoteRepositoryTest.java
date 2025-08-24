package com.repository;

import com.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class NoteRepositoryTest {

    private NoteRepository noteRepository;

    @BeforeEach
    void setUp() {
        noteRepository = new NoteRepository();
    }

    @Test
    void save_shouldAddNote() {
        Note note = new Note("Content");
        Note saved = noteRepository.save(note);

        assertEquals(note, saved);
        assertEquals(1, noteRepository.findAll().size());
    }

    @Test
    void findById_shouldReturnNoteIfExists() {
        Note note = new Note( "Content");
        noteRepository.save(note);
        String id = "123";
        note.setIdNote(id);

        Optional<Note> found = noteRepository.findById(id);
        assertTrue(found.isPresent());
        assertEquals(note, found.get());
    }
}
