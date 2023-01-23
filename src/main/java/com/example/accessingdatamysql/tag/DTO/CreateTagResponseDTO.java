package com.example.accessingdatamysql.tag.DTO;

public class CreateTagResponseDTO {

    private Integer tagId;
    private String tagName;

    public CreateTagResponseDTO(Integer tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
