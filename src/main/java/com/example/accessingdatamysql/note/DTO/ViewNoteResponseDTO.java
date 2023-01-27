package com.example.accessingdatamysql.note.DTO;

import jakarta.persistence.criteria.CriteriaBuilder;

public class ViewNoteResponseDTO {
    private Integer noteId;
    private String title;
    private String content;

    public ViewNoteResponseDTO(Integer noteId, String title, String content) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
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
