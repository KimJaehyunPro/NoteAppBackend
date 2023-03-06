package com.example.accessingdatamysql.note.DTO;

import com.example.accessingdatamysql.tag.DTO.TagResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public class NoteResponseDTO {
    private Integer id;
    private String title;
    private String content;
    private List<TagResponseDTO> tags;
    private LocalDateTime creationTimestamp;
    private LocalDateTime lastOpenTimestamp;

    public NoteResponseDTO(Integer id, String title, String content, List<TagResponseDTO> tags, LocalDateTime creationTimestamp, LocalDateTime lastOpenTimestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.creationTimestamp = creationTimestamp;
        this.lastOpenTimestamp = lastOpenTimestamp;
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

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public LocalDateTime getLastOpenTimestamp() {
        return lastOpenTimestamp;
    }

    public void setLastOpenTimestamp(LocalDateTime lastOpenTimestamp) {
        this.lastOpenTimestamp = lastOpenTimestamp;
    }
}
