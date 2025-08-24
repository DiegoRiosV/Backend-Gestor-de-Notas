package com.model;

public class Note {

    private String idNote;
    private String title;
    private String content;

    public Note() {
    }
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getIdNote() {
        return idNote;
    }
    public void setIdNote(String idNote) {
        this.idNote = idNote;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
