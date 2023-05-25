package com.example.accessingdatamysql.note;

import com.example.accessingdatamysql.authentication.AuthController;
import com.example.accessingdatamysql.note.DTO.NoteRequestDTO;
import com.example.accessingdatamysql.note.DTO.NoteResponseDTO;
import com.example.accessingdatamysql.tag.Tag;
import com.example.accessingdatamysql.tag.TagService;
import com.example.accessingdatamysql.user.User;
import com.example.accessingdatamysql.user.UserRepository;
import com.example.accessingdatamysql.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
public class NoteService {

    private  final UserRepository userRepository;
    private final NoteRepository noteRepository;
    private final TagService tagService;
    private final UserService userService;
    private final AuthController authController;

    public NoteService(UserRepository userRepository, NoteRepository noteRepository, TagService tagService, UserService userService, AuthController authController) {
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
        this.tagService = tagService;
        this.userService = userService;
        this.authController = authController;
    }

    @Transactional
    public NoteResponseDTO getNote(Integer id) {

        Optional<Note> noteOptional = findById(id);
        if (noteOptional.isEmpty()) {
            return null;
        }

        Note note = noteOptional.get();

        return toNoteResponseDTO(note);
    }

    @Transactional
    public boolean UpdateLastOpenTimestamp(Integer id) {

        Optional<Note> noteOptional = findById(id);
        if (noteOptional.isEmpty()) {
            return false;
        }

        Note note = noteOptional.get();
        note.setLastOpenTimestamp(LocalDateTime.now());
        noteRepository.save(note);

        return true;
    }

    public Page<Note> findNotes(String query, Pageable pageable) {

        Integer userId = authController.getUserId();

        if (query == null) {
            return findAllByUserId(pageable, userId);
        } else {
            return findAllByTitleOrContent(query, pageable, userId);
        }
    }

    @Transactional
    public Page<Note> findAllByTitleOrContent(String query, Pageable pageable, Integer userId) {
        return noteRepository.findAllByTitleOrContent(query, pageable, userId);
    }

    @Transactional
    public Page<Note> findAllByUserId(Pageable pageable, Integer userId) {
        return noteRepository.findAllByUserId(pageable, userId);
    }

    public NoteResponseDTO toNoteResponseDTO(Note note) {

        return new NoteResponseDTO(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                tagService.toTagResponseDTOList(note.getTags()),
                note.getCreationTimestamp(),
                note.getLastOpenTimestamp()
        );
    }

    public Page<NoteResponseDTO> toNoteResponseDTOsPage(Page<Note> notesPage) {
        return notesPage.map(this::toNoteResponseDTO);
    }

    @Transactional
    public Optional<Note> findById(Integer id) {
        return noteRepository.findById(id);
    }

    @Transactional
    public Page<Note> findAllByTag(String query, Pageable pageable) {
        Optional<Tag> tagOptional = tagService.findByName(query);

        if (tagOptional.isPresent()) {
            Tag tag = tagOptional.get();
            tagService.updateLastOpenTimestamp(tag);
        }

        Integer userId = authController.getUserId();

        return noteRepository.findAllByTag(query, pageable, userId);
    }

    @Transactional
    public Note createNote(String title, String content, List<String> tagList) {
        Integer authorUserId = authController.getUserId();
        User user = userService.getUserById(authorUserId);

        boolean userNotExists = (user == null);
        if (userNotExists) {
            return null;
        }

        Note newNote = new Note();
        newNote.setTitle(title);
        newNote.setContent(content);

        // Get all the tags
        Set<Tag> tagSet = tagService.getOrCreateTags(tagList);
        newNote.setTags(tagSet);

        user.getNotes().add(newNote);

        return noteRepository.save(newNote);
    }

    @Transactional
    public Integer getRandomId() {
        // Get a list of all notes from repository
        List<Note> notes = noteRepository.findAllByUserId(authController.getUserId());
        // Get a random index from the list
        Random random = new Random();
        int randomIndex = random.nextInt(notes.size());
        // Return the random index
        return notes.get(randomIndex).getId();
    }

    @Transactional
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

    @Transactional
    public boolean deleteNoteById(Integer id) {
        Optional<Note> noteToDeleteOptional = noteRepository.findById(id);

        if (noteToDeleteOptional.isPresent()) {
            Integer userId = authController.getUserId();

            List<Note> usersNotes = noteRepository.findAllByUserId(userId);
            Note noteToDelete = noteToDeleteOptional.get();

            // If this user is not the author, do not delete
            if (!usersNotes.contains(noteToDelete)) {
                return false;
            }

            // Delete the note's tags
            Set<Tag> tagsToCheck = noteToDelete.getTags();
            for (Tag tag : tagsToCheck) {
                Set<Note> noteSet = noteRepository.findAllByTag(tag.getName(), userId);
                if (noteSet.size() == 0) {
                    tagService.deleteTagById(tag.getId());
                }
            }

            // Delete the user's notes ??
            usersNotes.remove(noteToDelete);
            User user = userService.getUserById(userId);
            user.setNotes(usersNotes);

            // Delete the note
            noteRepository.delete(noteToDelete);

            return true;
        }
        return false;
    }
}
