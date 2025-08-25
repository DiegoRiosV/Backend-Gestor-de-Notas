package com.example.notas.model;

import jakarta.persistence.*;


@Entity
@Table(name = "notes")
public class Note {

    @Id 
    @Column(name = "note_id", length = 10)
    private String idNote;

    @Column(name = "note")
    private String content;

    public Note() {
    }
    public Note(String content) {
        this.content = content;
    }

    public String getIdNote() {
        return idNote;
    }
    public void setIdNote(String idNote) {
        this.idNote = idNote;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
