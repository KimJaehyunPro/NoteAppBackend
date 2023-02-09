package com.example.accessingdatamysql.note.DTO;

import com.example.accessingdatamysql.tag.DTO.TagRequestDTO;

import java.util.List;

public class NoteRequestDTO {
    private String title;
    private String content;
    private List<TagRequestDTO> tags;

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

    public List<TagRequestDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagRequestDTO> tags) {
        this.tags = tags;
    }
}
