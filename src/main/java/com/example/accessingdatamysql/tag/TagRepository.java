package com.example.accessingdatamysql.tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Integer>{

    Tag findByTagName(String tagName);
}
