package com.example.accessingdatamysql.note;
import com.example.accessingdatamysql.note.DTO.AddNewNoteResponseDTO;
import com.example.accessingdatamysql.note.DTO.EditNoteRequestDTO;
import com.example.accessingdatamysql.note.DTO.EditNoteResponseDTO;
import com.example.accessingdatamysql.note.DTO.GetAllNoteResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Optional<Note> viewNote(
            @RequestParam
            Integer id
    ) {
        Optional<Note> note = noteRepository.findById(id);
        return note;
    }

    public AddNewNoteResponseDTO addNewNote(
            String title,
            String content
    ) {

        Note newNote = new Note();
        newNote.setTitle(title);
        newNote.setContent(content);
        noteRepository.save(newNote);

        AddNewNoteResponseDTO addNewNoteResponseDTO = new AddNewNoteResponseDTO(newNote.getId(), title, content);
        return addNewNoteResponseDTO;
    }

    public List<GetAllNoteResponseDTO> getAllNotes() {

        Iterable<Note> allNotesConfidential = noteRepository.findAll();
        List<GetAllNoteResponseDTO> allNotesResponse = new ArrayList<>();

        for (Note note : allNotesConfidential) {
            GetAllNoteResponseDTO getAllNoteResponseDTO = new GetAllNoteResponseDTO(note.getId(), note.getTitle(), note.getContent());
            allNotesResponse.add(getAllNoteResponseDTO);
        }

        return allNotesResponse;
    }

    public EditNoteResponseDTO editNote(
            @RequestBody
            EditNoteRequestDTO editNoteRequestDTO
    ) {
        Integer noteId = editNoteRequestDTO.getNoteId();

        Optional<Note> optional = noteRepository.findById(noteId);
        if (optional.isEmpty()) { return null; };

        Note originalNote = optional.get();
        originalNote.setTitle(editNoteRequestDTO.getTitle());
        originalNote.setContent(editNoteRequestDTO.getContent());
        Note editedNote = noteRepository.save(originalNote);

        EditNoteResponseDTO editNoteResponseDTO = new EditNoteResponseDTO(
                editedNote.getId(), editedNote.getTitle(), editedNote.getContent()
        );

        return editNoteResponseDTO;
    }

    public String DeleteNote(
            @RequestBody
            Integer noteId
    ) {
        noteRepository.deleteById(noteId);
        return "Deleted!";
    }
}
