package com.example.accessingdatamysql.tag.DTO;

public class GetAllTagsResponseDTO {
    private String tagName;

    public GetAllTagsResponseDTO(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
