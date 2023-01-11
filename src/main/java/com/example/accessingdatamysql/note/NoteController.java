package com.example.accessingdatamysql.note;

import org.springframework.web.bind.annotation.*;

import com.example.accessingdatamysql.note.DTO.AddNewNoteRequestDTO;
import com.example.accessingdatamysql.note.DTO.AddNewNoteResponseDTO;
import com.example.accessingdatamysql.note.DTO.GetAllNoteResponseDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/note")
public class NoteController {

    public final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{noteId}")
    public Optional<Note> ViewNote(
            @PathVariable
            Integer noteId
    ) {
        return noteService.viewNote(noteId);
    }

    @GetMapping("/all")
    public List<GetAllNoteResponseDTO> getAllNotes() {
        return noteService.getAllNotes();
    }

    @PostMapping("/add")
    public AddNewNoteResponseDTO addNewNote(
            @RequestBody
            AddNewNoteRequestDTO addNewNoteRequestDTO
    ) {
        return noteService.addNewNote(addNewNoteRequestDTO.getTitle(), addNewNoteRequestDTO.getContent());
    }
}
