package com.example.accessingdatamysql.tag;

import com.example.accessingdatamysql.tag.DTO.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
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

    @CrossOrigin(origins = "*")
    @GetMapping("/")
    public Page<TagResponseDTO> getTags(
            @PageableDefault(page = 0, size = 5, sort = "lastOpenTimestamp", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return tagService.toTagResponseDTOsPage(tagService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public TagResponseDTO getTagById(
            @PathVariable
            Integer id
    ) {
        Optional<Tag> tagOptional = tagService.findById(id);
        if (tagOptional.isPresent()) {
            Tag tag = tagOptional.get();
            return tagService.toTagResponseDTO(tag);
        } else {
            return null;
        }
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
