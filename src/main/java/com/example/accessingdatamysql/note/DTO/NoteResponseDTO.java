package com.example.accessingdatamysql.note.DTO;

import com.example.accessingdatamysql.tag.DTO.TagResponseDTO;

import java.util.List;

public class NoteResponseDTO {
    private Integer id;
    private String title;
    private String content;
    private List<TagResponseDTO> tags;

    public NoteResponseDTO(Integer id, String title, String content, List<TagResponseDTO> tags) {
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

    public List<TagResponseDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagResponseDTO> tags) {
        this.tags = tags;
    }
}
