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
import java.util.Optional;

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

    @Test
    void deleteNote_shouldDeleteIfExists() {
        // Arrange
        when(noteRepository.existsById("id123")).thenReturn(true);
        doNothing().when(noteRepository).deleteById("id123");

        // Act
        noteService.deleteNote("id123");

        // Assert
        verify(noteRepository, times(1)).deleteById("id123");
    }

    @Test
    void updateNote_shouldUpdateContent() {
        // Arrange
        Note existing = new Note("Old content");
        existing.setIdNote("id123");

        Note newNote = new Note("New content");

        when(noteRepository.findById("id123")).thenReturn(Optional.of(existing));
        when(noteRepository.save(any(Note.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Note updated = noteService.updateNote("id123", newNote);

        // Assert
        assertEquals("New content", updated.getContent());
        verify(noteRepository, times(1)).findById("id123");
        verify(noteRepository, times(1)).save(existing);
    }

    @Test
    void updateNote_shouldThrowIfNotFound() {
        // Arrange
        Note newNote = new Note("New content");
        when(noteRepository.findById("id123")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> noteService.updateNote("id123", newNote));

        assertEquals("Note not found with id: id123", ex.getMessage());
    }
    @Test
    void updatePosition_shouldUpdateXandY() {
        // Arrange
        Note existing = new Note("Some content");
        existing.setIdNote("id123");
        existing.setPositionX(0);
        existing.setPositionY(0);

        when(noteRepository.findById("id123")).thenReturn(Optional.of(existing));
        when(noteRepository.save(any(Note.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        noteService.updatePosition("id123", 100, 200);

        // Assert
        assertEquals(100, existing.getPositionX());
        assertEquals(200, existing.getPositionY());
        verify(noteRepository, times(1)).findById("id123");
        verify(noteRepository, times(1)).save(existing);
    }
    @Test
    void updatePosition_shouldThrowIfNotFound() {
        // Arrange
        when(noteRepository.findById("id123")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> noteService.updatePosition("id123", 100, 200));

        assertEquals("Note not found with id: id123", ex.getMessage());
    }

}