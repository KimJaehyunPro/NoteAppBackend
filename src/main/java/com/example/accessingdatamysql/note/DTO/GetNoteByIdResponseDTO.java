package com.example.accessingdatamysql.note.DTO;

import com.example.accessingdatamysql.tag.Tag;

import java.util.Set;

public class GetNoteByIdResponseDTO {
    private Integer noteId;
    private String title;
    private String content;
    private Set<Tag> tags;

    public GetNoteByIdResponseDTO(Integer noteId, String title, String content, Set<Tag> tags) {
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}