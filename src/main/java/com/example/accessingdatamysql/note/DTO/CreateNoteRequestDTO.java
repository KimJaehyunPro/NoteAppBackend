package com.example.accessingdatamysql.note.DTO;

import java.util.List;

public class CreateNoteRequestDTO {
    private String title;
    private String content;
    private List<String> tagNames;

    public CreateNoteRequestDTO(String title, String content, List<String> tagNames) {
        this.title = title;
        this.content = content;
        this.tagNames = tagNames;
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
