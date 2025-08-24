package com.service;

import com.model.Note;
import com.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoteServiceTest {

    private NoteService noteService;
    private NoteRepository noteRepository;

    @BeforeEach
    void setUp() {
        noteRepository = new NoteRepository(); // repositorio temporal
        noteService = new NoteService(noteRepository);
    }

    @Test
    void saveNote_shouldGenerate10CharIdAndSave() {
        Note note = new Note("Test Title", "Test Content");
        Note saved = noteService.saveNote(note);

        // Verificar que se generó id de 10 caracteres
        assertNotNull(saved.getIdNote());
        assertEquals(10, saved.getIdNote().length());

        // Verificar que se guardó en repositorio
        List<Note> allNotes = noteService.getAllNotes();
        assertTrue(allNotes.contains(saved));

        // Verificar título y contenido
        assertEquals("Test Title", saved.getTitle());
        assertEquals("Test Content", saved.getContent());
    }

    @Test
    void getAllNotes_shouldReturnAllSavedNotes() {
        Note note1 = new Note("Title1", "Content1");
        Note note2 = new Note("Title2", "Content2");

        noteService.saveNote(note1);
        noteService.saveNote(note2);

        List<Note> notes = noteService.getAllNotes();
        assertEquals(2, notes.size());
        assertTrue(notes.contains(note1));
        assertTrue(notes.contains(note2));
    }
}
