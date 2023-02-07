package com.example.accessingdatamysql.note;

import com.example.accessingdatamysql.note.DTO.*;
import com.example.accessingdatamysql.tag.Tag;
import com.example.accessingdatamysql.tag.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    /**
     * Searches Notes that contain 'query' in either title or content.
     * @param query a String, Case-insensitive
     * @param pageable a Pageable
     * @return Page of Notes
     */
    public Page<Note> findByTitleOrContent(String query, Pageable pageable) {
        return noteRepository.findByTitleContainingOrContentContaining(query, query, pageable);
    }

    public Page<Note> getNotesPage(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }

    public Page<NoteResponseDTO> toNoteResponseDTOsPage(Page<Note> notesPage) {
                return notesPage.map(this::toNoteResponseDTO);
    }

    public NoteResponseDTO toNoteResponseDTO(Note note) {
                NoteResponseDTO noteResponseDTO = new NoteResponseDTO(
                        note.getId(),
                        note.getTitle(),
                        note.getContent(),
                        tagService.convertTagSetToStringList(note.getTags())
                );

                return noteResponseDTO;
    }

    public List<NoteResponseDTO> notesListToNoteResponseDTOsList(List<Note> notesList) {
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

    public Optional<Note> findById(Integer id) {
        return noteRepository.findById(id);
    }

    public Page<Note> findAllByTag(String query, Pageable pageable) {
        return noteRepository.findAllByTag(query, pageable);
    }

    public Note createNote(String title, String content, List<String> tagList) {

        Note newNote = new Note();
        newNote.setTitle(title);
        newNote.setContent(content);

        // Get all the tags
        Set<Tag> tagSet = tagService.getOrCreateTags(tagList);
        newNote.setTags(tagSet);

        Note createdNote = noteRepository.save(newNote);

        return createdNote;
    }

    public Note getRandomNote() {
        return null;
    }

    public Note updateNote(Integer id, NoteRequestDTO noteRequestDTO) {
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

    public boolean deleteNoteById(Integer id) {
        Optional<Note> noteToDelete = noteRepository.findById(id);
        if (noteToDelete.isPresent()) {
            noteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
