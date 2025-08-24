package com.model;

public class Note {

    private String idNote;
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
