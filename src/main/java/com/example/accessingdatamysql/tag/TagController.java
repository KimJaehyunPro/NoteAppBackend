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

//    @GetMapping("/all")
//    public List<GetAllTagsResponseDTO> getAllTags() {
//
//        List<GetAllTagsResponseDTO> allTagsResponse = new ArrayList<>();
//
//        List<Tag> allTags = tagService.getAllTags();
//        for (Tag tag : allTags){
//            GetAllTagsResponseDTO getAllTagsResponseDTO = new GetAllTagsResponseDTO(tag.getId(), tag.getName());
//            allTagsResponse.add(getAllTagsResponseDTO);
//        }
//        return allTagsResponse;
//    }


    @GetMapping("/{id}")
    public TagResponseDTO getTagById(
            @PathVariable
            Integer id
    ) {
        Optional<Tag> tagOptional = tagService.findById(id);
        if (tagOptional.isEmpty()) return null;
        return tagService.toTagResponseDTO(tagOptional.get());
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
        return null;
    }

//    @PostMapping("/create")
//    public CreateTagResponseDTO createTag(
//            @RequestBody
//            CreateTagRequestDTO createTagRequestDTO
//    ) {
//        // Service should do all or most of the logic.
//        Tag tag = tagService.createTag(createTagRequestDTO.getTagName());
//
//        // The only logic that controller should do is converting it to DTO response.
//        CreateTagResponseDTO dto = new CreateTagResponseDTO(tag.getId(), tag.getTagName());
//        return dto;
//    }
//
//    @DeleteMapping("/delete")
//    public DeleteTagResponseDTO deleteTag(
//            @RequestBody
//            DeleteTagRequestDTO deleteTagRequestDTO
//    ) {
//        return tagService.deleteTag(deleteTagRequestDTO);
//    }
}
