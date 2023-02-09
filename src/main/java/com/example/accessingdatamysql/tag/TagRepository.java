package com.example.accessingdatamysql.tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findById(Integer id);
    Optional<Tag> findByTagName(String tagName);
}
