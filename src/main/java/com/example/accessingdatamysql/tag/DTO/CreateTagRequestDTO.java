package com.example.accessingdatamysql.tag.DTO;

public class CreateTagRequestDTO {
    private String tagName;

    public CreateTagRequestDTO(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
