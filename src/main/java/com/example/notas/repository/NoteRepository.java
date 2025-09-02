package com.example.notas.repository;

import org.springframework.stereotype.Repository;
import com.example.notas.model.Note;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {
    List<Note> findByCategoryCategoryId(String categoryId);
    List<Note> findByContentContainingIgnoreCase(String searchTerm);
    List<Note> findByCategoryCategoryIdAndContentContainingIgnoreCase(String categoryId, String searchTerm);
}
