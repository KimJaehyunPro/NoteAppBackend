package com.example.accessingdatamysql.note;

import com.example.accessingdatamysql.note.DTO.NoteRequestDTO;
import com.example.accessingdatamysql.note.DTO.NoteResponseDTO;
import com.example.accessingdatamysql.tag.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/notes")
public class NoteController {

    public final NoteService noteService;
    public final TagService tagService;

    public NoteController(NoteService noteService, TagService tagService) {
        this.noteService = noteService;
        this.tagService = tagService;
    }

    @GetMapping("/")
    public Page<NoteResponseDTO> getNotes(
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<Note> notePage = noteService.getNotesPage(pageable);

        return noteService.toNoteResponseDTOsPage(notePage);
    }

    @GetMapping("/{id}")
    public NoteResponseDTO getNote(
            @PathVariable
            Integer id
    ) {
        Optional<Note> noteOptional = noteService.findById(id);

        return noteOptional.map(noteService::toNoteResponseDTO).orElse(null);
    }

    @GetMapping("/randomNoteId")
    public NoteResponseDTO getRandomNoteId() {
        return noteService.toNoteResponseDTO(noteService.getRandomNote());
    }

    @PostMapping("/")
    public NoteResponseDTO createNote(
            @RequestBody
            NoteRequestDTO noteRequestDTO
    ) {

        Note createdNote = noteService.createNote(
                noteRequestDTO.getTitle(),
                noteRequestDTO.getContent(),
                noteRequestDTO.getTags()
        );

        return noteService.toNoteResponseDTO(createdNote);
    }

    @PutMapping("/{id}")
    public NoteResponseDTO updateNote(
            @PathVariable
            Integer id,
            @RequestBody
            NoteRequestDTO noteRequestDTO
    ) {
        Note updatedNote = noteService.updateNote(id, noteRequestDTO);

        return noteService.toNoteResponseDTO(updatedNote);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteNote(
            @PathVariable
            Integer id
    ) {
        return noteService.deleteNoteById(id);
    }

}
