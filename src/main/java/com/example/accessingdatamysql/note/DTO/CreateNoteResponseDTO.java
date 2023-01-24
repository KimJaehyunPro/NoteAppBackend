package com.example.accessingdatamysql.note.DTO;

import java.util.List;

public class CreateNoteResponseDTO {

    private Integer noteId;
    private String title;
    private String content;

    private List<String> tagNames;

    public CreateNoteResponseDTO(Integer noteId, String title, String content, List<String> tagNames) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.tagNames = tagNames;
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

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }
}
