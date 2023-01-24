package com.example.accessingdatamysql.tag;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag, Integer>{

    Optional<Tag> findByTagName(String tagName);
}
