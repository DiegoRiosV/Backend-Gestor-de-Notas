package com.service;

import com.example.notas.model.Note;
import com.example.notas.repository.NoteRepository;
import com.example.notas.service.NoteService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) 
class NoteServiceTest {

    @Mock 
    private NoteRepository noteRepository;

    @InjectMocks 
    private NoteService noteService;


    @Test
    void saveNote_shouldGenerate10CharIdAndSave() {
        // Arrange
        Note noteToSave = new Note("Test Content");

        when(noteRepository.save(any(Note.class))).thenAnswer(invocation -> {
            Note note = invocation.getArgument(0);
            assertNotNull(note.getIdNote());
            assertEquals(10, note.getIdNote().length());
            return note;
        });

        // Act
        Note saved = noteService.saveNote(noteToSave);

        // Assert
        assertNotNull(saved.getIdNote());
        assertEquals(10, saved.getIdNote().length());
        assertEquals("Test Content", saved.getContent());

        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void getAllNotes_shouldReturnAllSavedNotes() {
        // Arrange
        Note note1 = new Note("Content1");
        note1.setIdNote("id1");
        Note note2 = new Note("Content2");
        note2.setIdNote("id2");
        List<Note> expectedNotes = List.of(note1, note2);

        when(noteRepository.findAll()).thenReturn(expectedNotes);

        // Act
        List<Note> notes = noteService.getAllNotes();

        // Assert
        assertEquals(2, notes.size());
        assertEquals(expectedNotes, notes);
        
        verify(noteRepository, times(1)).findAll();
    }


}