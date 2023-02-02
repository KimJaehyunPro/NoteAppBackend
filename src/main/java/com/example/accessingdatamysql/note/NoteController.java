package com.example.accessingdatamysql.note;

import com.example.accessingdatamysql.note.DTO.*;
import com.example.accessingdatamysql.tag.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/note")
public class NoteController {

    public final NoteService noteService;
    public final TagService tagService;

    public NoteController(NoteService noteService, TagService tagService) {
        this.noteService = noteService;
        this.tagService = tagService;
    }

    @GetMapping("/{noteId}")
    public GetNoteByIdResponseDTO GetNoteById(
            @PathVariable
            Integer noteId
    ) {
        Note note = noteService.findById(noteId);
        return new GetNoteByIdResponseDTO(note.getId(), note.getTitle(), note.getContent(), note.getTags());
    }

    @PostMapping("/GetNoteByTitle")
    public GetNoteByTitleResponseDTO GetNoteByTitle(
            @RequestBody
            GetNoteByTitleRequestDTO getNoteByTitleRequestDTO
    ) {
        Note note = noteService.findByTitle(getNoteByTitleRequestDTO.getTitle());
        return new GetNoteByTitleResponseDTO(note.getId(), note.getTitle(), note.getContent(), note.getTags());
    }

    @GetMapping("/randomNoteId")
    public RandomNoteIdResponseDTO getRandomNoteId() {
        return noteService.getRandomNoteId();
    }

//    @PostMapping("/getSomeNotes")
//    public List<GetSomeNotesResponseDTO> getSomeNotes(
//            @RequestBody
//            GetSomeNotesRequestDTO getSomeNotesRequestDTO
//    ) {
//        return noteService.getSomeNotes(getSomeNotesRequestDTO.getNumberOfNotes(), getSomeNotesRequestDTO.getPage());
//    }

    @GetMapping("/all")
    public List<GetAllNoteResponseDTO> getAllNotes() {
        return noteService.getAllNotes();
    }

    @PostMapping("/create")
    public CreateNoteResponseDTO createNote(
            @RequestBody
            CreateNoteRequestDTO createNoteRequestDTO
    ) {
        Note createdNote = noteService.createNote(createNoteRequestDTO.getTitle(), createNoteRequestDTO.getContent(), createNoteRequestDTO.getTagNames());
        List<String> tagStringList = tagService.convertTagSetToStringList(createdNote.getTags());
        return new CreateNoteResponseDTO(
                createdNote.getId(),
                createdNote.getTitle(),
                createdNote.getContent(),
                tagStringList
        );
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

}
