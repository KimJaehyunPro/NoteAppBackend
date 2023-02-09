package com.example.accessingdatamysql.tag;

import com.example.accessingdatamysql.tag.DTO.TagRequestDTO;
import com.example.accessingdatamysql.tag.DTO.TagResponseDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Set<Tag> getTags(List<String> tagNames) {

        Set<Tag> tags = new HashSet<>();

        for (String tagName : tagNames) {
            Optional<Tag> tagOptional = findByTagName(tagName);

            Boolean tagIsPresent = (tagOptional.isPresent());
            if (tagIsPresent) {
                tags.add(tagOptional.get());
            }
        }

        return tags;
    }

    public TagResponseDTO toTagResponseDTO(Tag tag) {
        return new TagResponseDTO(tag.getId(), tag.getName());
    }

    public Optional<Tag> findByTagName(String tagName) {
        return tagRepository.findByName(tagName);
    }

    public Optional<Tag> findById(Integer id) {
        return tagRepository.findById(id);
    }

    public List<Tag> getAllTags() {
        List<Tag> tagsList = new ArrayList<>();
        Iterable<Tag> tagsIterable = tagRepository.findAll();

        tagsIterable.forEach(
                tagsList::add
        );
        return tagsList;
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

//    public DeleteTagResponseDTO deleteTag(DeleteTagRequestDTO deleteTagRequestDTO) {
//        Integer tagId = deleteTagRequestDTO.getTagId();
//        tagRepository.deleteById(tagId);
//
//        return new DeleteTagResponseDTO(tagId);
//    }

    /**
     * Convert a set of Tag into a list of Tag so that you can be used in response DTOs.
     * @param tags a Set of Tags.
     * @return List of strings of tags' name.
     */
    public List<String> getListOfTagNames(Set<Tag> tags) {

        List<String> tagNamesStrings = new ArrayList<>();

        for (Tag tag : tags) {
            tagNamesStrings.add(tag.getName());
        }

        return tagNamesStrings;
    }

    public List<String> convertTagSetToStringList(Set<Tag> tagSet) {
        return tagSet.stream().map(tag -> tag.getName()).toList();
    }

    public Set<Tag> convertStringListToTagSet(List<String> TagStringList) {
        List<String> tagStringList = TagStringList;
        Set<Tag> tagSet = new HashSet<>();

        for (String tagString : tagStringList) {
            Tag tag = new Tag();
            tag.setName(tagString);
            tagSet.add(tag);
        }

        return tagSet;
    }

    /**
     * Get a list of tagNames, if it doesn't exist, create tag(s)
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

    public Tag getOrCreateTag (String tagName) {
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
