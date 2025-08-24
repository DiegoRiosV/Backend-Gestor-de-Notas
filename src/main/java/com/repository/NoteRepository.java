package com.repository;

import com.model.Note;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class NoteRepository {

    private final List<Note> notes = new ArrayList<>();

    public Note save(Note note) {
        notes.add(note);
        return note;
    }

    public List<Note> findAll() {
        return new ArrayList<>(notes);
    }

    public Optional<Note> findById(String id) {
        return notes.stream().filter(n -> n.getIdNote().equals(id)).findFirst();
    }

    public void delete(Note note) {
        notes.remove(note);
    }
}
