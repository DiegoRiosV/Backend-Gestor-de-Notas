package com.example.notas.controller;

import com.example.notas.model.Note;
import com.example.notas.service.NoteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*") // permite llamadas desde Angular
@Tag(name = "Gestión de Notas", description = "Endpoints para el CRUD completo y búsqueda de notas.")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // POST /api/notes
    @Operation(summary = "Crear una nueva nota")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nota creada exitosamente."),
        @ApiResponse(responseCode = "400", description = "Petición inválida (ej. contenido vacío).")
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> createNote(@RequestBody Note note) {
        Map<String, Object> response = new HashMap<>();
        try {
            Note savedNote = noteService.saveNote(note);
            response.put("success", true);
            response.put("message", "Note saved successfully");
            response.put("note", savedNote);
            return ResponseEntity.ok(response);
        
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Eliminar una nota por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nota eliminada exitosamente."),
        @ApiResponse(responseCode = "404", description = "La nota no fue encontrada.")
    })
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

    @Operation(summary = "Actualizar una nota existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nota actualizada exitosamente."),
        @ApiResponse(responseCode = "404", description = "La nota no fue encontrada.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable String id, @RequestBody Note newNote) {
        try {
            Note updatedNote = noteService.updateNote(id, newNote);
            return ResponseEntity.ok(updatedNote);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Operation(
        summary = "Obtener notas con filtros opcionales",
        description = "Devuelve una lista de notas. Se puede filtrar por categoría, por término de búsqueda en el contenido, o ambos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa. Devuelve una lista de notas (puede estar vacía).")
    })
    @GetMapping
    public ResponseEntity<List<Note>> getNotes(
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) String search
    ) {
        List<Note> notes = noteService.searchAndFilterNotes(categoryId, search);
        return ResponseEntity.ok(notes); 
    }
}
