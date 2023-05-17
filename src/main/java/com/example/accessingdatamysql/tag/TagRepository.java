package com.example.accessingdatamysql.tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    //@Query("SELECT Tag FROM User user JOIN user.notes note JOIN note.tags tag WHERE user.id = :userId")
    @Query("SELECT tag FROM User user JOIN user.notes note JOIN note.tags tag WHERE user.id = :userId")
    Page<Tag> findAll(Pageable pageable, Integer userId);
    Optional<Tag> findByName(String name);
}
