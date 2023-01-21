package com.example.accessingdatamysql.tag;

import com.example.accessingdatamysql.tag.DTO.CreateTagRequestDTO;
import com.example.accessingdatamysql.tag.DTO.CreateTagResponseDTO;
import com.example.accessingdatamysql.tag.DTO.GetAllTagsResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    private TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<GetAllTagsResponseDTO> getAllTags() {
        Iterable<Tag> allTags = tagRepository.findAll();
        List<GetAllTagsResponseDTO> allTagsResponse = new ArrayList<>();

        for (Tag tag : allTags){
            GetAllTagsResponseDTO getAllTagsResponseDTO = new GetAllTagsResponseDTO(tag.getTagName());
            allTagsResponse.add(getAllTagsResponseDTO);
        }
        return allTagsResponse;
    }

    public CreateTagResponseDTO createTag(CreateTagRequestDTO createTagRequestDTO) {

        String tagName = createTagRequestDTO.getTagName();
        Tag tag = new Tag();
        tag.setTagName(tagName);

        tagRepository.save(tag);

        CreateTagResponseDTO createTagResponseDTO = new CreateTagResponseDTO(tagName);
        return createTagResponseDTO;
    }
}
