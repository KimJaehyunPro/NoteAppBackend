package com.example.accessingdatamysql.note;

import com.example.accessingdatamysql.note.DTO.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
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

    @PostMapping("/create")
    public CreateNoteResponseDTO createNote(
            @RequestBody
            CreateNoteRequestDTO createNoteRequestDTO
    ) {
        return noteService.createNote(createNoteRequestDTO.getTitle(), createNoteRequestDTO.getContent());
    }

    @PostMapping("/delete")
    public DeleteNoteResponseDTO deleteNote(
            @RequestBody
            DeleteNoteRequestDTO deleteNoteRequestDTO
    ) {
        return noteService.deleteNote(deleteNoteRequestDTO);
    }

    @PostMapping("/update")
    public UpdateNoteResponseDTO updateNote(
            @RequestBody
            UpdateNoteRequestDTO updateNoteRequestDTO
    ) {
        return noteService.updateNote(updateNoteRequestDTO);
    }

    @GetMapping("/randomNoteId")
    public RandomNoteIdResponseDTO getRandomNoteId() {
        return noteService.getRandomNoteId();
    }
}
