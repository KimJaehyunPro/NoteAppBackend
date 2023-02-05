package com.example.accessingdatamysql.note;

import com.example.accessingdatamysql.note.DTO.*;
import com.example.accessingdatamysql.tag.Tag;
import com.example.accessingdatamysql.tag.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public List<Note> getNotesList(
            Pageable pageable
    ) {
        return getNotesPage(pageable).toList();
    }

    public Page<Note> getNotesPage(
            Pageable pageable
    ) {
        return noteRepository.findAll(pageable);
    }

    public List<NoteResponseDTO> notesListToNoteResponseDTOsList(
            List<Note> notesList
    ) {
        List<NoteResponseDTO> noteResponseDTOList = new ArrayList<>();

        for (Note note : notesList) {
            NoteResponseDTO NoteResponseDTO = new NoteResponseDTO(
                    note.getId(),
                    note.getTitle(),
                    note.getContent(),
                    tagService.convertTagSetToStringList(note.getTags()));
            noteResponseDTOList.add(NoteResponseDTO);
        }
        return noteResponseDTOList;
    }



    public Note findById(
            Integer id
    ) {
        Optional<Note> noteOptional = noteRepository.findById(id);

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
            List<String> tagList
    ) {

        Note newNote = new Note();
        newNote.setTitle(title);
        newNote.setContent(content);

        // Get all the tags
        Set<Tag> tagSet = tagService.getOrCreateTags(tagList);
        newNote.setTags(tagSet);

        Note createdNote = noteRepository.save(newNote);

        return createdNote;
    }

    public List<GetAllNoteResponseDTO> getAllNotes() {

        Pageable pageable = PageRequest.of(0, 5, Sort.by("Id").descending());
        Page<Note> allNotesConfidential = noteRepository.findAll(pageable);

        List<GetAllNoteResponseDTO> allNotesResponse = new ArrayList<>();

        for (Note note : allNotesConfidential) {
            GetAllNoteResponseDTO getAllNoteResponseDTO = new GetAllNoteResponseDTO(note.getId(), note.getTitle(), note.getContent(), note.getTags());
            allNotesResponse.add(getAllNoteResponseDTO);
        }

        return allNotesResponse;
    }

    public Integer getRandomNoteId() {

        Iterable<Note> allNotesConfidential = noteRepository.findAll();
        List<Integer> allNoteIds = new ArrayList<>();

        for (Note note : allNotesConfidential) {
            Integer randomNoteId = note.getId();
            allNoteIds.add(randomNoteId);
        }

        Random random = new Random();

        Integer randomNoteId = allNoteIds.get(random.nextInt(allNoteIds.size()));

        return randomNoteId;
    }

    public Note updateNote(
            Integer id,
            NoteRequestDTO noteRequestDTO
    ) {
        Optional<Note> noteOptional = noteRepository.findById(id);
        if (noteOptional.isEmpty()) {
            return null;
        }

        Note originalNote = noteOptional.get();

        originalNote.setTitle(noteRequestDTO.getTitle());
        originalNote.setContent(noteRequestDTO.getContent());

        Set<Tag> newTags = tagService.getOrCreateTags(noteRequestDTO.getTags());
        originalNote.setTags(newTags);

        return noteRepository.save(originalNote);
    }

//    public UpdateNoteResponseDTO updateNote(
//            UpdateNoteRequestDTO updateNoteRequestDTO
//    ) {
//        Integer noteId = updateNoteRequestDTO.getNoteId();
//
//        Optional<Note> optional = noteRepository.findById(noteId);
//        if (optional.isEmpty()) {
//            return null;
//        }
//
//        Note originalNote = optional.get();
//
//        originalNote.setTitle(updateNoteRequestDTO.getTitle());
//        originalNote.setContent(updateNoteRequestDTO.getContent());
//
//        Set<Tag> retrievedTags = tagService.getOrCreateTags(updateNoteRequestDTO.getTagNames());
//        originalNote.setTags(retrievedTags);
//
//        Note editedNote = noteRepository.save(originalNote);
//
//        return new UpdateNoteResponseDTO(editedNote.getId(), editedNote.getTitle(), editedNote.getContent(), tagService.convertTagSetToStringList(retrievedTags));
//    }

    public DeleteNoteResponseDTO deleteNote(
            DeleteNoteRequestDTO deleteNoteRequestDTO
    ) {
        Integer noteId = deleteNoteRequestDTO.getNoteId();
        noteRepository.deleteById(noteId);

        return new DeleteNoteResponseDTO(noteId);
    }

    public Page<Note> findAll(
            Pageable pageable
    ) {
        return noteRepository.findAll(pageable);
    }
}
