package com.controller;

import com.example.notas.controller.NoteController;
import com.example.notas.model.Note;
import com.example.notas.repository.NoteRepository;
import com.example.notas.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class NoteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
        mockMvc = MockMvcBuilders.standaloneSetup(new NoteController(noteService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createNote_shouldReturnSavedNote() throws Exception {
        // Mock para que cualquier Note devuelva una con idNote asignado
        when(noteService.saveNote(any(Note.class))).thenAnswer(invocation -> {
            Note n = invocation.getArgument(0);
            n.setIdNote("1234567890"); // ID generado para test
            return n;
        });

        Note note = new Note("Test Content");

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.note.idNote").value("1234567890"))
                .andExpect(jsonPath("$.note.content").value("Test Content"));
    }

    @Test
    void getAllNotes_shouldReturnListOfNotes() throws Exception {
        Note note1 = new Note("Content1");
        note1.setIdNote("id1");
        Note note2 = new Note("Content2");
        note2.setIdNote("id2");

        when(noteService.getAllNotes()).thenReturn(Arrays.asList(note1, note2));

        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idNote").value("id1"))
                .andExpect(jsonPath("$[1].idNote").value("id2"));
    }

    @Test
    void deleteNote_shouldReturnSuccess() throws Exception {
        doNothing().when(noteService).deleteNote("id123");

        mockMvc.perform(delete("/api/notes/id123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Note deleted successfully"));

        verify(noteService, times(1)).deleteNote("id123");
    }

    @Test
    void deleteNote_shouldReturn404IfNotFound() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Note not found with id: id123"))
                .when(noteService).deleteNote("id123");

        // Act & Assert
        mockMvc.perform(delete("/api/notes/id123"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Note not found with id: id123"));
    }

    @Test
    void updateNote_shouldReturnUpdatedNote() throws Exception {
        // Arrange
        Note updated = new Note("Updated content");
        updated.setIdNote("id123");

        when(noteService.updateNote(eq("id123"), any(Note.class))).thenReturn(updated);

        Note body = new Note("Updated content");

        // Act & Assert
        mockMvc.perform(put("/api/notes/id123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idNote").value("id123"))
                .andExpect(jsonPath("$.content").value("Updated content"));
    }

}
