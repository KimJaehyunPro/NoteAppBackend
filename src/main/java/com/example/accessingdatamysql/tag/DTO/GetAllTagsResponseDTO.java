package com.example.accessingdatamysql.tag.DTO;

public class GetAllTagsResponseDTO {

    private Integer id;
    private String tagName;

    public GetAllTagsResponseDTO(Integer id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
