package com.example.accessingdatamysql.tag;

import com.example.accessingdatamysql.tag.DTO.CreateTagRequestDTO;
import com.example.accessingdatamysql.tag.DTO.CreateTagResponseDTO;
import com.example.accessingdatamysql.tag.DTO.DeleteTagRequestDTO;
import com.example.accessingdatamysql.tag.DTO.DeleteTagResponseDTO;
import com.example.accessingdatamysql.tag.DTO.GetAllTagsResponseDTO;

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
            Optional<Tag> optionalTag = tagRepository.findByTagName(tagName);
            if (optionalTag.isPresent()) {
                tags.add(optionalTag.get());
            }
        }

        return tags;
    }

    public List<Tag> getAllTags() {
        List<Tag> tagsList = new ArrayList<>();
        Iterable<Tag> tagsIterable = tagRepository.findAll();

        tagsIterable.forEach(
                tagsList::add
        );
        return tagsList;
    }

    public Tag createTag(String tagName) {
        Tag tag = new Tag();
        tag.setTagName(tagName);
        Tag created = tagRepository.save(tag);

        return created;
    }

    public DeleteTagResponseDTO deleteTag(DeleteTagRequestDTO deleteTagRequestDTO) {
        Integer tagId = deleteTagRequestDTO.getTagId();
        tagRepository.deleteById(tagId);

        return new DeleteTagResponseDTO(tagId);
    }

    /**
     * Convert a set of Tag into a list of Tag so that you can be used in response DTOs.
     * @param tags a Set of Tags.
     * @return List of strings of tags' name.
     */
    public List<String> getListOfTagNames(Set<Tag> tags) {

        List<String> tagNamesStrings = new ArrayList<>();

        for (Tag tag : tags) {
            tagNamesStrings.add(tag.getTagName());
        }

        return tagNamesStrings;
    }

    public List<String> convertTagSetToStringList(Set<Tag> tagSet) {
        return tagSet.stream().map(tag -> tag.getTagName()).toList();
    }

    public Set<Tag> convertStringListToTagSet(List<String> TagStringList) {
        List<String> tagStringList = TagStringList;
        Set<Tag> tagSet = new HashSet<>();

        for (String tagString : tagStringList) {
            Tag tag = new Tag();
            tag.setTagName(tagString);
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
        Optional<Tag> tag = tagRepository.findByTagName(tagName);
        if (tag.isPresent()) {
            return tag.get();
        } else {
            Tag tagToCreate = new Tag();
            tagToCreate.setTagName(tagName);
            Tag createdTag = tagRepository.save(tagToCreate);
            return createdTag;
        }
    }
}
