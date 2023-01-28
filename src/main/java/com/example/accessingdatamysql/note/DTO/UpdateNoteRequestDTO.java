package com.example.accessingdatamysql.note.DTO;

import com.example.accessingdatamysql.tag.Tag;

import java.util.List;

public class UpdateNoteRequestDTO {
    private Integer noteId;
    private String title;
    private String content;
    private List<String> tags;

    public UpdateNoteRequestDTO(Integer noteId, String title, String content, List<String> tags) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.tags = tags;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
