package com.example.accessingdatamysql.note;

import com.example.accessingdatamysql.note.DTO.NoteRequestDTO;
import com.example.accessingdatamysql.note.DTO.NoteResponseDTO;
import com.example.accessingdatamysql.tag.Tag;
import com.example.accessingdatamysql.tag.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

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
     *
     * @param query    a String, Case-insensitive
     * @param pageable a Pageable
     * @return Page of Notes
     */

    public Page<Note> findNotes(String query, Pageable pageable) {
        if (query == null) {
            return findAll(pageable);
        } else {
            return findAllByTitleOrContent(query, pageable);
        }
    }

    public Page<Note> findAllByTitleOrContent(String query, Pageable pageable) {
        return noteRepository.findAllByTitleContainingOrContentContaining(query, query, pageable);
    }

    public Page<Note> findAll(Pageable pageable) {
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
                tagService.toTagResponseDTOList(note.getTags())
        );

        return noteResponseDTO;
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

    public Integer getRandomId() {
        // Get a list of all notes from repository
        List<Note> notes = noteRepository.findAll();
        // Get a random index from the list
        Random random = new Random();
        Integer randomIndex = random.nextInt(notes.size());

        // Return the random index
        return notes.get(randomIndex).getId();
    }

    public Note updateNote(Integer id, NoteRequestDTO noteRequestDTO) {
        Optional<Note> noteOptional = noteRepository.findById(id);
        if (noteOptional.isEmpty()) {
            return null;
        }

        Note originalNote = noteOptional.get();

        originalNote.setTitle(noteRequestDTO.getTitle());
        originalNote.setContent(noteRequestDTO.getContent());

        List<String> tagsToGetOrCreate = tagService.toStringList(noteRequestDTO.getTags());
        Set<Tag> newTags = tagService.getOrCreateTags(tagsToGetOrCreate);
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
