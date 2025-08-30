package com.example.notas.repository;

import org.springframework.stereotype.Repository;
import com.example.notas.model.Note;


import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {

}
