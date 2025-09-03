package com.example.notas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;


@Entity
@Table(name = "notes")
@Schema(description = "Represents a single note.")
public class Note {

    @Id 
    @Column(name = "note_id", length = 10)
    @Schema(description = "Unique identifier for the note, auto-generated.", example = "a1b2c3d4e5")
    private String idNote;

    @Column(name = "note")
    @Schema(description = "The main content of the note.", example = "Reunión de equipo a las 10 AM.")
    private String content;

    @Column(name = "position_x")
    @Schema(description = "The X coordinate for the note's position on the board.", example = "150")
    private Integer positionX;

    @Column(name = "position_y")
    @Schema(description = "The Y coordinate for the note's position on the board.", example = "200")
    private Integer positionY;

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "category_id")
    @Schema(description = "The category to which the note belongs.")
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
