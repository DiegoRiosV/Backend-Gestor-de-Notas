package com.example.notas.controller;

import com.example.notas.model.Note;
import com.example.notas.service.NoteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*") // permite llamadas desde Angular
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // POST /api/notes
    @PostMapping
    public ResponseEntity<Map<String, Object>> createNote(@RequestBody Note note) {
        Map<String, Object> response = new HashMap<>();
        try {
            Note savedNote = noteService.saveNote(note);
            response.put("success", true);
            response.put("message", "Note saved successfully");
            response.put("note", savedNote);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<java.util.List<Note>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteNote(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {
            noteService.deleteNote(id);
            response.put("success", true);
            response.put("message", "Note deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
