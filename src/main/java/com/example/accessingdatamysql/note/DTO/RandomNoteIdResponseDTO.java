package com.example.accessingdatamysql.note.DTO;

public class RandomNoteIdResponseDTO {
    private Integer noteId;

    public RandomNoteIdResponseDTO(Integer noteId) {
        this.noteId = noteId;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }
}
