package com.example.accessingdatamysql.tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Page<Tag> findAll(Pageable pageable);
    Optional<Tag> findByName(String name);
}
