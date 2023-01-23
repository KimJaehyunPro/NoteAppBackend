package com.example.accessingdatamysql.tag.DTO;

public class DeleteTagResponseDTO {

    private Integer tagId;

    public DeleteTagResponseDTO(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}
