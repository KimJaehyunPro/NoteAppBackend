package com.example.accessingdatamysql.note.DTO;

public class DeleteNoteRequestDTO {
    private Integer noteId;
    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }
}
