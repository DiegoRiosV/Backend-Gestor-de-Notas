package com.controller;

import com.example.notas.controller.NoteController;
import com.example.notas.model.Category;
import com.example.notas.model.Note;
import com.example.notas.repository.NoteRepository;
import com.example.notas.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class NoteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createNote_shouldReturnSavedNoteWithPositionAndCategory() throws Exception {
        Category category = new Category();
        category.setCategoryId("work");

        Note noteToSave = new Note("Test Content");
        noteToSave.setPositionX(100);
        noteToSave.setPositionY(150);
        noteToSave.setCategory(category);

        Note savedNote = new Note(noteToSave.getContent());
        savedNote.setIdNote("1234567890");
        savedNote.setPositionX(noteToSave.getPositionX());
        savedNote.setPositionY(noteToSave.getPositionY());
        savedNote.setCategory(noteToSave.getCategory());

        when(noteService.saveNote(any(Note.class))).thenReturn(savedNote);

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteToSave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.note.idNote").value("1234567890"))
                .andExpect(jsonPath("$.note.content").value("Test Content"))
                .andExpect(jsonPath("$.note.positionX").value(100))
                .andExpect(jsonPath("$.note.positionY").value(150))
                .andExpect(jsonPath("$.note.category.categoryId").value("work"));
    }

    @Test
    void createNote_whenContentIsEmpty_shouldReturnBadRequest() throws Exception {
        when(noteService.saveNote(any(Note.class)))
                .thenThrow(new IllegalArgumentException("Note content cannot be empty."));

        Note noteWithEmptyContent = new Note("");

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteWithEmptyContent)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Note content cannot be empty."));
    }

    @Test
    void getAllNotes_shouldReturnListOfNotes() throws Exception {
        Note note1 = new Note("Content1");
        note1.setIdNote("id1");
        Note note2 = new Note("Content2");
        note2.setIdNote("id2");
        List<Note> notes = Arrays.asList(note1, note2);

        when(noteService.getAllNotes()).thenReturn(notes);

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
        doThrow(new RuntimeException("Note not found with id: id123"))
                .when(noteService).deleteNote("id123");

        mockMvc.perform(delete("/api/notes/id123"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Note not found with id: id123"));
    }

    @Test
    void updateNote_shouldReturnUpdatedNote() throws Exception {
        Category category = new Category();
        category.setCategoryId("personal");

        Note updatedNote = new Note("Updated content");
        updatedNote.setIdNote("id123");
        updatedNote.setPositionX(200);
        updatedNote.setPositionY(250);
        updatedNote.setCategory(category);

        when(noteService.updateNote(eq("id123"), any(Note.class))).thenReturn(updatedNote);

        Note requestBody = new Note("Updated content");
        requestBody.setPositionX(200);
        requestBody.setPositionY(250);
        requestBody.setCategory(category);

        mockMvc.perform(put("/api/notes/id123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idNote").value("id123"))
                .andExpect(jsonPath("$.content").value("Updated content"))
                .andExpect(jsonPath("$.positionX").value(200))
                .andExpect(jsonPath("$.positionY").value(250))
                .andExpect(jsonPath("$.category.categoryId").value("personal"));
    }
}
