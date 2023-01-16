package com.example.accessingdatamysql.note.DTO;

public class DeleteNoteResponseDTO {
    private Integer noteId;
    public DeleteNoteResponseDTO(
            Integer noteId
    ) {
        this.noteId = noteId;
    }
    public Integer getNoteId() {
        return noteId;
    }
    public void setNoteId(
            Integer noteId
    ) {
        this.noteId = noteId;
    }
}
