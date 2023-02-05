package com.example.accessingdatamysql.note;

import com.example.accessingdatamysql.note.DTO.NoteRequestDTO;
import com.example.accessingdatamysql.note.DTO.NoteResponseDTO;
import com.example.accessingdatamysql.tag.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void deleteNote(
            @PathVariable
            Integer id
    ) {
        return;
    }
//
//    @GetMapping("/getSomeNotes")
//    public List<GetSomeNotesResponseDTO> getSomeNotes(
//            @RequestBody
//            GetSomeNotesRequestDTO getSomeNotesRequestDTO
//    ) {
//        return noteService.getSomeNotes(getSomeNotesRequestDTO.getNumberOfNotes(), getSomeNotesRequestDTO.getPage());
//    }
//
//    @GetMapping("/{noteId}")
//    public GetNoteByIdResponseDTO GetNoteById(
//            @PathVariable
//            Integer noteId
//    ) {
//        Note note = noteService.findById(noteId);
//        return new GetNoteByIdResponseDTO(note.getId(), note.getTitle(), note.getContent(), note.getTags());
//    }
//
//    @PostMapping("/GetNoteByTitle")
//    public GetNoteByTitleResponseDTO GetNoteByTitle(
//            @RequestBody
//            GetNoteByTitleRequestDTO getNoteByTitleRequestDTO
//    ) {
//        Note note = noteService.findByTitle(getNoteByTitleRequestDTO.getTitle());
//        return new GetNoteByTitleResponseDTO(note.getId(), note.getTitle(), note.getContent(), note.getTags());
//    }
//
//    @GetMapping("/randomNoteId")
//    public RandomNoteIdResponseDTO getRandomNoteId() {
//        return noteService.getRandomNoteId();
//    }
//
//
//
//    @GetMapping("/all")
//    public List<GetAllNoteResponseDTO> getAllNotes() {
//        return noteService.getAllNotes();
//    }
//
//    @PostMapping("/create")
//    public CreateNoteResponseDTO createNote(
//            @RequestBody
//            CreateNoteRequestDTO createNoteRequestDTO
//    ) {
//        Note createdNote = noteService.createNote(createNoteRequestDTO.getTitle(), createNoteRequestDTO.getContent(), createNoteRequestDTO.getTagNames());
//        List<String> tagStringList = tagService.convertTagSetToStringList(createdNote.getTags());
//        return new CreateNoteResponseDTO(
//                createdNote.getId(),
//                createdNote.getTitle(),
//                createdNote.getContent(),
//                tagStringList
//        );
//    }
//
//    @PostMapping("/delete")
//    public DeleteNoteResponseDTO deleteNote(
//            @RequestBody
//            DeleteNoteRequestDTO deleteNoteRequestDTO
//    ) {
//        return noteService.deleteNote(deleteNoteRequestDTO);
//    }
//
//    @PostMapping("/update")
//    public UpdateNoteResponseDTO updateNote(
//            @RequestBody
//            UpdateNoteRequestDTO updateNoteRequestDTO
//    ) {
//        return noteService.updateNote(updateNoteRequestDTO);
//    }
//
//    @GetMapping("/foo")
//    public Page<Note> findAll(
//            @PageableDefault(value = 5, page = 0, sort = "id", direction = Sort.Direction.DESC)
//            Pageable pageable) {
//        return noteService.findAll(pageable);
//    }

}
