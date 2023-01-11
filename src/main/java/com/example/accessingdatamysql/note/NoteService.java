package com.example.accessingdatamysql.note;
import com.example.accessingdatamysql.note.DTO.AddNewNoteResponseDTO;
import com.example.accessingdatamysql.note.DTO.GetAllNoteResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class NoteService {

    private NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public String viewNote() {
        return "test successful!";
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
}
