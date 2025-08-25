package com.example.notas.repository;

import org.springframework.stereotype.Repository;

import com.example.notas.model.Note;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {

}

// public class NoteRepository {

//     private final List<Note> notes = new ArrayList<>();

//     public Note save(Note note) {
//         notes.add(note);
//         return note;
//     }

//     public List<Note> findAll() {
//         return new ArrayList<>(notes);
//     }

//     public Optional<Note> findById(String id) {
//         return notes.stream().filter(n -> n.getIdNote().equals(id)).findFirst();
//     }

// }
