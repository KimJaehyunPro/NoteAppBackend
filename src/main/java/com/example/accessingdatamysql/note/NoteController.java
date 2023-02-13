package com.example.accessingdatamysql.note;

import com.example.accessingdatamysql.note.DTO.NoteRequestDTO;
import com.example.accessingdatamysql.note.DTO.NoteResponseDTO;
import com.example.accessingdatamysql.tag.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/notes")
public class NoteController {

    public final NoteService noteService;
    public final TagService tagService;

    public NoteController(NoteService noteService, TagService tagService) {
        this.noteService = noteService;
        this.tagService = tagService;
    }

    @GetMapping("/search/")
    public Page<NoteResponseDTO> getNotePageByTitleOrContent(
            @RequestParam(required = true)
            String query,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return noteService.toNoteResponseDTOsPage(noteService.findAllByTitleOrContent(query, pageable));
    }

    @GetMapping("/search/tag")
    public Page<NoteResponseDTO> getNotePageByTag(
            @RequestParam(required = true)
            String query,
            @PageableDefault(page=0, size=5, sort="id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<Note> notesPage = noteService.findAllByTag(query, pageable);

        return noteService.toNoteResponseDTOsPage(notesPage);
    }

    @GetMapping("/")
    public Page<NoteResponseDTO> getNotePage(
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<Note> notePage = noteService.findAll(pageable);

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
                tagService.toStringList(noteRequestDTO.getTags())
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
        Boolean isDeleted = noteService.deleteNoteById(id);
        if (!isDeleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Delete not successful");
        }
        return true;
    }

}
