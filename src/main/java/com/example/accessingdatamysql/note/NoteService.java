package com.example.accessingdatamysql.note;

import com.example.accessingdatamysql.note.DTO.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class NoteService {

    private NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Optional<Note> viewNote(
            Integer id
    ) {
        Optional<Note> note = noteRepository.findById(id);
        return note;
    }

    public CreateNoteResponseDTO createNote(
            String title,
            String content
    ) {

        Note newNote = new Note();
        newNote.setTitle(title);
        newNote.setContent(content);
        noteRepository.save(newNote);

        CreateNoteResponseDTO createNoteResponseDTO = new CreateNoteResponseDTO(newNote.getId(), title, content);
        return createNoteResponseDTO;
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

    public RandomNoteIdResponseDTO getRandomNoteId() {

        Iterable<Note> allNotesConfidential = noteRepository.findAll();
        List<Integer> allNoteIds = new ArrayList<>();

        for (Note note : allNotesConfidential) {
            Integer randomNoteId = note.getId();
            allNoteIds.add(randomNoteId);
        }

        Random random = new Random();

        Integer randomNoteId = allNoteIds.get(random.nextInt(allNoteIds.size()));

        RandomNoteIdResponseDTO randomNoteIdResponseDTO = new RandomNoteIdResponseDTO(randomNoteId);

        return randomNoteIdResponseDTO;
    }

    public UpdateNoteResponseDTO updateNote(
            UpdateNoteRequestDTO updateNoteRequestDTO
    ) {
        Integer noteId = updateNoteRequestDTO.getNoteId();

        Optional<Note> optional = noteRepository.findById(noteId);
        if (optional.isEmpty()) {
            return null;
        }
        ;

        Note originalNote = optional.get();
        originalNote.setTitle(updateNoteRequestDTO.getTitle());
        originalNote.setContent(updateNoteRequestDTO.getContent());
        Note editedNote = noteRepository.save(originalNote);

        UpdateNoteResponseDTO updateNoteResponseDTO = new UpdateNoteResponseDTO(
                editedNote.getId(), editedNote.getTitle(), editedNote.getContent()
        );

        return updateNoteResponseDTO;
    }

    public DeleteNoteResponseDTO deleteNote(
            DeleteNoteRequestDTO deleteNoteRequestDTO
    ) {
        Integer noteId = deleteNoteRequestDTO.getNoteId();
        noteRepository.deleteById(noteId);
        DeleteNoteResponseDTO deleteNoteResponseDTO = new DeleteNoteResponseDTO(noteId);
        return deleteNoteResponseDTO;
    }
}
