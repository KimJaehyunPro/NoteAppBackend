package com.example.accessingdatamysql.note;

import com.example.accessingdatamysql.note.DTO.NoteRequestDTO;
import com.example.accessingdatamysql.note.DTO.NoteResponseDTO;
import com.example.accessingdatamysql.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/notes")
public class NoteController {

    public final NoteService noteService;
    public final TagService tagService;

    @Autowired
    public NoteController(NoteService noteService, TagService tagService) {
        this.noteService = noteService;
        this.tagService = tagService;
    }

    @GetMapping("/search")
    public Page<NoteResponseDTO> getNotePageByTitleOrContent(
            @RequestParam(required = false)
            String query,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return noteService.toNoteResponseDTOsPage(noteService.findNotes(query, pageable));
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

    @GetMapping("/{id}")
    public NoteResponseDTO getNote(
            @PathVariable
            Integer id
    ) {
        return noteService.getNote(id);
    }

    @PutMapping("/updateLastOpenTimestamp/{id}")
    public Boolean updateLastOpenTimestamp(
            @PathVariable
            Integer id
    ) {
        return noteService.UpdateLastOpenTimestamp(id);
    }

    @GetMapping("/randomId")
    public Integer getRandomId() {
        return noteService.getRandomId();
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
        boolean isDeleted = noteService.deleteNoteById(id);
        if (!isDeleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Delete not successful");
        }
        return true;
    }

}
