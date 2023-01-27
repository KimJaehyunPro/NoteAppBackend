package com.example.accessingdatamysql.tag;

import com.example.accessingdatamysql.tag.DTO.CreateTagRequestDTO;
import com.example.accessingdatamysql.tag.DTO.CreateTagResponseDTO;
import com.example.accessingdatamysql.tag.DTO.GetAllTagsResponseDTO;
import com.example.accessingdatamysql.tag.DTO.DeleteTagRequestDTO;
import com.example.accessingdatamysql.tag.DTO.DeleteTagResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/all")
    public List<GetAllTagsResponseDTO> getAllTags() {

        List<GetAllTagsResponseDTO> allTagsResponse = new ArrayList<>();

        Iterable<Tag> allTags = tagService.getAllTags();
        for (Tag tag : allTags){
            GetAllTagsResponseDTO getAllTagsResponseDTO = new GetAllTagsResponseDTO(tag.getId(), tag.getTagName());
            allTagsResponse.add(getAllTagsResponseDTO);
        }
        return allTagsResponse;
    }

    @PostMapping("/create")
    public CreateTagResponseDTO createTag(
            @RequestBody
            CreateTagRequestDTO createTagRequestDTO
    ) {
        // Service should do all or most of the logic.
        Tag tag = tagService.createTag(createTagRequestDTO.getTagName());

        // The only logic that controller should do is converting it to DTO response.
        CreateTagResponseDTO dto = new CreateTagResponseDTO(tag.getId(), tag.getTagName());
        return dto;
    }

    @PostMapping("/delete")
    public DeleteTagResponseDTO deleteTag(
            @RequestBody
            DeleteTagRequestDTO deleteTagRequestDTO
    ) {
        return tagService.deleteTag(deleteTagRequestDTO);
    }
}
