package com.example.accessingdatamysql.tag.DTO;

import java.time.LocalDateTime;

public class TagResponseDTO {
    private Integer id;
    private String name;
    private LocalDateTime lastOpenTimestamp;

    public TagResponseDTO(Integer id, String name, LocalDateTime lastOpenTimestamp) {
        this.id = id;
        this.name = name;
        this.lastOpenTimestamp = lastOpenTimestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLastOpenTimestamp() {
        return lastOpenTimestamp;
    }

    public void setLastOpenTimestamp(LocalDateTime lastOpenTimestamp) {
        this.lastOpenTimestamp = lastOpenTimestamp;
    }
}
