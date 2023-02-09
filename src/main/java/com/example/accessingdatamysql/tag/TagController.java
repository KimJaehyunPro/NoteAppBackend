package com.example.accessingdatamysql.tag;

import com.example.accessingdatamysql.tag.DTO.*;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    public TagResponseDTO getTagById(
            @PathVariable
            Integer id
    ) {
        Optional<Tag> tagOptional = tagService.findById(id);
        if (tagOptional.isEmpty()) return null;
        return tagService.toTagResponseDTO(tagOptional.get());
    }

    @GetMapping("/")
    public TagResponseDTO getTagByName(
            @RequestBody
            TagRequestDTO tagRequestDTO
    ) {

        Optional<Tag> tagOptional = tagService.findByName(tagRequestDTO.getName());
        if (tagOptional.isEmpty()) return null;

        Tag tag = tagOptional.get();
        return tagService.toTagResponseDTO(tag);
    }

    @PostMapping("/")
    public TagResponseDTO createTag(
            @RequestBody
            TagRequestDTO tagRequestDTO
    ) {
        Tag createdTag = tagService.createTag(tagRequestDTO.getName());
        return tagService.toTagResponseDTO(createdTag);
    }

    @PutMapping("/{id}")
    public TagResponseDTO updateTag(
            @PathVariable
            Integer id,
            @RequestBody
            TagRequestDTO tagRequestDTO
    ) {
        Tag updatedTag = tagService.updateTag(id, tagRequestDTO);
        return tagService.toTagResponseDTO(updatedTag);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteTag(
            @PathVariable
            Integer id
    ) {
        return tagService.deleteTagById(id);
    }

}
