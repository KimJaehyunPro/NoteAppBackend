package com.example.accessingdatamysql.note;

import com.example.accessingdatamysql.note.DTO.NoteRequestDTO;
import com.example.accessingdatamysql.note.DTO.NoteResponseDTO;
import com.example.accessingdatamysql.tag.TagService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<NoteResponseDTO> getNotes(
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        List<NoteResponseDTO> noteResponseDTOList = noteService.notesListToNoteResponseDTOsList(noteService.getNotesList(pageable));
        return noteResponseDTOList;
    }

    @GetMapping("/{id}")
    public NoteResponseDTO getNote(
            @PathVariable
            Integer id
    ) {
        Note note = noteService.findById(id);

        return new NoteResponseDTO(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                tagService.convertTagSetToStringList(note.getTags())
        );
    }

    @GetMapping("/randomNoteId")
    public Integer getRandomNoteId() {
        return noteService.getRandomNoteId();
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

        NoteResponseDTO noteResponseDTO = new NoteResponseDTO(
                createdNote.getId(),
                createdNote.getTitle(),
                createdNote.getContent(),
                tagService.convertTagSetToStringList(createdNote.getTags())
        );

        return noteResponseDTO;
    }

    @PutMapping("/{id}")
    public NoteResponseDTO updateNote(
            @PathVariable
            Integer id,
            @RequestBody
            NoteRequestDTO noteRequestDTO
    ) {
        Note updatedNote = noteService.updateNote(id, noteRequestDTO);

        NoteResponseDTO noteResponseDTO = new NoteResponseDTO(
                updatedNote.getId(),
                updatedNote.getTitle(),
                updatedNote.getContent(),
                tagService.convertTagSetToStringList(updatedNote.getTags())
        );

        return noteResponseDTO;
    }

    @DeleteMapping("/{id}")
    public Boolean deleteNote(
            @PathVariable
            Integer id
    ) {
        return noteService.deleteNoteById(id);
    }

}
