package com.example.accessingdatamysql.tag;

import com.example.accessingdatamysql.authentication.AuthController;
import com.example.accessingdatamysql.note.Note;
import com.example.accessingdatamysql.tag.DTO.TagRequestDTO;
import com.example.accessingdatamysql.tag.DTO.TagResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TagService {

    private final AuthController authController;
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository, AuthController authController) {
        this.tagRepository = tagRepository;
        this.authController = authController;
    }

    public TagResponseDTO toTagResponseDTO(Tag tag) {
        return new TagResponseDTO(tag.getId(), tag.getName(), tag.getLastOpenTimestamp());
    }

    public Optional<Tag> findByName(String tagName) {
        return tagRepository.findByName(tagName);
    }

    public Optional<Tag> findById(Integer id) {
        return tagRepository.findById(id);
    }

    public Page<Tag> findAll(Pageable pageable) {
        Integer userId = authController.getUserId();
        return tagRepository.findAll(pageable, userId);
    }

    public Set<Tag> findAllByNoteId(Integer noteId) {
        return tagRepository.findAllByNoteId(noteId);
    }

    public Tag updateLastOpenTimestamp (Tag tag) {
        tag.setLastOpenTimestamp(LocalDateTime.now());
        return tagRepository.save(tag);
    }

    public Tag createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        Tag createdTag = tagRepository.save(tag);

        return createdTag;
    }

    public Tag updateTag(Integer id, TagRequestDTO tagRequestDTO) {

        Optional<Tag> tagOptional = tagRepository.findById(id);
        if (tagOptional.isEmpty()) return null;

        Tag tag = tagOptional.get();
        tag.setName(tagRequestDTO.getName());

        return tagRepository.save(tag);
    }

    @Transactional
    public boolean deleteTagById(Integer id) {

        Optional<Tag> tagOptional = tagRepository.findById(id);
        if (tagOptional.isEmpty()) return false;

        //Delete the tag from all Notes that has that tag
        Tag tagToDelete = tagOptional.get();
        for (Note note : tagToDelete.getNotes()) {
            note.getTags().remove(tagToDelete);
        }

        tagRepository.deleteById(id);
        return true;
    }

    public List<TagResponseDTO> toTagResponseDTOList(Set<Tag> tagSet) {
        return tagSet.stream().map(tag -> new TagResponseDTO(tag.getId(), tag.getName(), tag.getLastOpenTimestamp())).toList();
    }

    public Page<TagResponseDTO> toTagResponseDTOsPage(Page<Tag> tagsPage) {
        return tagsPage.map(this::toTagResponseDTO);
    }

    public List<String> toStringList(List<TagRequestDTO> tagRequestDTOList) {
        return tagRequestDTOList.stream().map(TagRequestDTO::getName).toList();
    }

    /**
     * Get a list of tagNames, if it doesn't exist, create tag(s)
     *
     * @param tagNames a list of tag names
     * @return a set of created or exisiting tags
     */
    public Set<Tag> getOrCreateTags(List<String> tagNames) {

        Set<Tag> tagSet = new HashSet<>();

        for (String tagName : tagNames) {
            Tag tag = getOrCreateTag(tagName);
            tagSet.add(tag);
        }
        return tagSet;
    }

    public Tag getOrCreateTag(String tagName) {
        Optional<Tag> tag = tagRepository.findByName(tagName);
        if (tag.isPresent()) {
            return tag.get();
        } else {
            Tag tagToCreate = new Tag();
            tagToCreate.setName(tagName);
            Tag createdTag = tagRepository.save(tagToCreate);
            return createdTag;
        }
    }
}
