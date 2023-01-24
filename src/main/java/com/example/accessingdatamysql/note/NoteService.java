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

    public Optional<Note> viewNote(
            Integer id
    ) {
        return noteRepository.findById(id);
    }

    public CreateNoteResponseDTO createNoteWithoutComments(
            String title,
            String content,
            List<String> tagNames
    ) {

        Note newNote = new Note();
        newNote.setTitle(title);
        newNote.setContent(content);
        newNote.setTags(tagService.getTags(tagNames));

        // Convert List of Tag(newNote.getTags) into List of String
        // Iterate over newNote.getTags() and convert every element(Tag) into String
        List<String> tagSetString = new ArrayList<String>();

        for (Tag tag : newNote.getTags()) {
            tagSetString.add(tag.getTagName());
        }

        noteRepository.save(newNote);

        return new CreateNoteResponseDTO(newNote.getId(), title, content, tagSetString);
    }

    // TODO: Make this method to return Note, not CreateNoteResponseDTO. Converting to CreateNoteResponseDT from Note can be done in controller instead.
    /**
     * Create a note with title, content, and tag names.
     * @param title a String
     * @param content a String
     * @param tagNames a list<String>
     * @return a CreateNoteResponseDTO with id, title, content, and createdTagNames.
     */
    public CreateNoteResponseDTO createNote(
            String title,
            String content,
            List<String> tagNames
    ) {

        // Create a new Note with title and content given as inputs
        Note newNote = new Note();
        newNote.setTitle(title);
        newNote.setContent(content);

        // Get Tags that correspond to the tagNames. If the Tags don't exist, create them and return.
        Set<Tag> tags = tagService.getOrCreateTags(tagNames);
        // Set the new Note's tags with the tags you've retrieved from getOrCreateTags.
        newNote.setTags(tags);
        // Save the new Note
        Note createdNote = noteRepository.save(newNote);
        List<String> retrievedTagNamesList = tagService.getListOfTagNames(createdNote.getTags());

        return new CreateNoteResponseDTO(newNote.getId(), title, content, retrievedTagNamesList);
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
        Note editedNote = noteRepository.save(originalNote);

        return new UpdateNoteResponseDTO(editedNote.getId(), editedNote.getTitle(), editedNote.getContent());
    }

    public DeleteNoteResponseDTO deleteNote(
            DeleteNoteRequestDTO deleteNoteRequestDTO
    ) {
        Integer noteId = deleteNoteRequestDTO.getNoteId();
        noteRepository.deleteById(noteId);

        return new DeleteNoteResponseDTO(noteId);
    }
}
