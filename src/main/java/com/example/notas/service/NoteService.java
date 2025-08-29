package com.example.notas.service;

import com.example.notas.model.Note;
import com.example.notas.repository.NoteRepository;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    private String generateId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    public Note saveNote(Note note) {
        if (note.getContent() == null || note.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Note content cannot be empty.");
        }

        if (note.getPositionX() == null) {
            note.setPositionX(0); // Default X position
        }

        if (note.getPositionY() == null) {
            note.setPositionY(0); // Default Y position
        }
        
        note.setIdNote(generateId());
        return noteRepository.save(note);
    }

    public java.util.List<Note> getAllNotes() {
        return noteRepository.findAll();
    }


    public void deleteNote(String id) {
        if (!noteRepository.existsById(id)) {
            throw new RuntimeException("Note not found with id: " + id);
        }
        noteRepository.deleteById(id);
    }

    public Note updateNote(String id, Note newNoteDetails){
        return noteRepository.findById(id).map(note -> {
            
            if (newNoteDetails.getContent() != null && !newNoteDetails.getContent().trim().isEmpty()) {
                note.setContent(newNoteDetails.getContent());
            }
            
            if (newNoteDetails.getPositionX() != null) {
                note.setPositionX(newNoteDetails.getPositionX());
            }
            if (newNoteDetails.getPositionY() != null) {
                note.setPositionY(newNoteDetails.getPositionY());
            }
            if (newNoteDetails.getCategory() != null) {
                note.setCategory(newNoteDetails.getCategory());
            }
            return noteRepository.save(note);
        }).orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
    }
}
