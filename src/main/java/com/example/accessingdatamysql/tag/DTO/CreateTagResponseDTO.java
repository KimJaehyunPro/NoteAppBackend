package com.example.accessingdatamysql.tag.DTO;

public class CreateTagResponseDTO {
    private String tagName;

    public CreateTagResponseDTO(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
