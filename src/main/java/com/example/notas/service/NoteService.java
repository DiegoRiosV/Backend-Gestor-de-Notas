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


    public void deleteNote(String id) {
        if (!noteRepository.existsById(id)) {
            throw new RuntimeException("Note not found with id: " + id);
        }
        noteRepository.deleteById(id);
    }

    public Note updateNote(String id, Note newNote){
        return noteRepository.findById(id).map(note ->{
            note.setContent(newNote.getContent());
            return noteRepository.save(note);
        }).orElseThrow(() -> new RuntimeException("Note not found with id: "+id));
    }
}
