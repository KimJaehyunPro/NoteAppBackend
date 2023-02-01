package com.example.accessingdatamysql.note.DTO;

import com.example.accessingdatamysql.tag.Tag;

import java.util.Set;

public class GetSomeNotesRequestDTO {
    private Integer numberOfNotes;
    private Integer page;

    public GetSomeNotesRequestDTO(Integer numberOfNotes, Integer page) {
        this.numberOfNotes = numberOfNotes;
        this.page = page;
    }

    public Integer getNumberOfNotes() {
        return numberOfNotes;
    }

    public void setNumberOfNotes(Integer numberOfNotes) {
        this.numberOfNotes = numberOfNotes;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
