package com.example.accessingdatamysql.note;

import com.example.accessingdatamysql.note.DTO.*;
import org.springframework.web.bind.annotation.*;
import com.example.accessingdatamysql.tag.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/note")
public class NoteController {

    public final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{noteId}")
    public GetNoteResponseDTO GetNote(
            @PathVariable
            Integer noteId
    ) {
        Note note = noteService.findById(noteId);
        return new GetNoteResponseDTO(note.getId(), note.getTitle(), note.getContent());
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
        Note createdNote = noteService.createNote(createNoteRequestDTO.getTitle(), createNoteRequestDTO.getContent(), createNoteRequestDTO.getTagNames());
        return new CreateNoteResponseDTO(createdNote.getId(), createdNote.getTitle(), createdNote.getContent(), convertTagSetToStringList(createdNote.getTags()));
    }

    private List<String> convertTagSetToStringList(Set<Tag> tagSet) {

        List<String> tagStringList = new ArrayList<>();

        for (Tag tag : tagSet) {
            tagStringList.add(tag.getTagName());
        }

        return tagStringList;
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
