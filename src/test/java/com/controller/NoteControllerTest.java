package com.controller;

import com.model.Note;
import com.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class NoteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NoteService noteService;

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

        Note note = new Note("Test Title", "Test Content");

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.note.idNote").value("1234567890"))
                .andExpect(jsonPath("$.note.title").value("Test Title"))
                .andExpect(jsonPath("$.note.content").value("Test Content"));
    }

    @Test
    void getAllNotes_shouldReturnListOfNotes() throws Exception {
        Note note1 = new Note("Title1", "Content1");
        note1.setIdNote("id1");
        Note note2 = new Note("Title2", "Content2");
        note2.setIdNote("id2");

        when(noteService.getAllNotes()).thenReturn(Arrays.asList(note1, note2));

        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idNote").value("id1"))
                .andExpect(jsonPath("$[1].idNote").value("id2"));
    }
}
