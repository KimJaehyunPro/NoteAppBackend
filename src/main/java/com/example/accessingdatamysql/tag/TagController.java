package com.example.accessingdatamysql.tag;

import com.example.accessingdatamysql.note.NoteService;
import com.example.accessingdatamysql.tag.DTO.CreateTagRequestDTO;
import com.example.accessingdatamysql.tag.DTO.CreateTagResponseDTO;
import com.example.accessingdatamysql.tag.DTO.GetAllTagsResponseDTO;
import com.example.accessingdatamysql.tag.DTO.DeleteTagRequestDTO;
import com.example.accessingdatamysql.tag.DTO.DeleteTagResponseDTO;
import org.hibernate.sql.Delete;
import org.springframework.web.bind.annotation.*;

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
        return tagService.getAllTags();
    }

    @PostMapping("/create")
    public CreateTagResponseDTO createTag(
            @RequestBody
            CreateTagRequestDTO tagName
    ) {
        return tagService.createTag(tagName);
    }

    @PostMapping("/delete")
    public DeleteTagResponseDTO deleteTag(
            @RequestBody
            DeleteTagRequestDTO deleteTagRequestDTO
    ) {
        return tagService.deleteTag(deleteTagRequestDTO);
    }
}
