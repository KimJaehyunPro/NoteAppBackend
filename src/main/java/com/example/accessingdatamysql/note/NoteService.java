package com.example.accessingdatamysql.note;

import com.example.accessingdatamysql.note.DTO.*;
import com.example.accessingdatamysql.tag.Tag;
import com.example.accessingdatamysql.tag.TagService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final TagService tagService;

    public NoteService(NoteRepository noteRepository, TagService tagService) {
        this.noteRepository = noteRepository;
        this.tagService = tagService;
    }

    public Note findById(
            Integer noteId
    ) {
        Optional<Note> noteOptional = noteRepository.findById(noteId);

        Note note = noteOptional.get();
        return note;
    }

    public Note findByTitle(
            String title
    ) {
        Optional<Note> noteOptional = noteRepository.findNoteByTitle(title);
        Note note = noteOptional.get();
        return note;
    }

    public Note createNote(
            String title,
            String content,
            List<String> tagNames
    ) {

        Note newNote = new Note();
        newNote.setTitle(title);
        newNote.setContent(content);

        // Get all the tags
        Set<Tag> tags = tagService.getOrCreateTags(tagNames);
        newNote.setTags(tags);

        Note createdNote = noteRepository.save(newNote);

        return createdNote;
    }

    public List<GetAllNoteResponseDTO> getAllNotes() {

        Iterable<Note> allNotesConfidential = noteRepository.findAll();
        List<GetAllNoteResponseDTO> allNotesResponse = new ArrayList<>();

        for (Note note : allNotesConfidential) {
            GetAllNoteResponseDTO getAllNoteResponseDTO = new GetAllNoteResponseDTO(note.getId(), note.getTitle(), note.getContent(), note.getTags());
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

        return new RandomNoteIdResponseDTO(randomNoteId);
    }

    public UpdateNoteResponseDTO updateNote(
            UpdateNoteRequestDTO updateNoteRequestDTO
    ) {
        Integer noteId = updateNoteRequestDTO.getNoteId();

        Optional<Note> optional = noteRepository.findById(noteId);
        if (optional.isEmpty()) {
            return null;
        }

        Note originalNote = optional.get();

        originalNote.setTitle(updateNoteRequestDTO.getTitle());
        originalNote.setContent(updateNoteRequestDTO.getContent());

        Set<Tag> retrievedTags = tagService.getOrCreateTags(updateNoteRequestDTO.getTags());
        originalNote.setTags(retrievedTags);

        Note editedNote = noteRepository.save(originalNote);

        return new UpdateNoteResponseDTO(editedNote.getId(), editedNote.getTitle(), editedNote.getContent(), tagService.convertTagSetToStringList(retrievedTags));
    }

    public DeleteNoteResponseDTO deleteNote(
            DeleteNoteRequestDTO deleteNoteRequestDTO
    ) {
        Integer noteId = deleteNoteRequestDTO.getNoteId();
        noteRepository.deleteById(noteId);

        return new DeleteNoteResponseDTO(noteId);
    }
}
