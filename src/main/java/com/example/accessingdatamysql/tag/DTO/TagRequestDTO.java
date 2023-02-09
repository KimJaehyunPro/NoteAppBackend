package com.example.accessingdatamysql.tag.DTO;

public class TagRequestDTO {
    private String name;

    public TagRequestDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
