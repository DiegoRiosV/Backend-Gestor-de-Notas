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

    @Column(name = "position_x")
    private Integer positionX;

    @Column(name = "position_y")
    private Integer positionY;

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "category_id")
    private Category category;

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

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
