package com.example.accessingdatamysql.tag;

import com.example.accessingdatamysql.tag.DTO.*;
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
