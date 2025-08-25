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
        note.setIdNote(generateId());
        return noteRepository.save(note);
    }

    public java.util.List<Note> getAllNotes() {
        return noteRepository.findAll();
    }
}
