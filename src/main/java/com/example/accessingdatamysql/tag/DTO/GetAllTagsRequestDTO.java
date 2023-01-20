package com.example.accessingdatamysql.tag.DTO;

public class GetAllTagsRequestDTO {
    private String tagName;

    public GetAllTagsRequestDTO(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
