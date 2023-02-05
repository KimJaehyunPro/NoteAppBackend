package com.example.accessingdatamysql.note.DTO;

import java.util.List;

public class NoteResponseDTO {
    private Integer id;
    private String title;
    private String content;
    private List<String> tags;

    public NoteResponseDTO(Integer id, String title, String content, List<String> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
