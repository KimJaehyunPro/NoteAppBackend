package com.example.accessingdatamysql.tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    //@Query("SELECT Tag FROM User user JOIN user.notes note JOIN note.tags tag WHERE user.id = :userId")
    @Query("SELECT tag FROM User user JOIN user.notes note JOIN note.tags tag WHERE user.id = :userId")
    Page<Tag> findAll(Pageable pageable, Integer userId);
    @Query("SELECT tag FROM Note note JOIN note.tags tag WHERE note.id = :noteId")
    Set<Tag> findAllByNoteId(Integer noteId);
    Optional<Tag> findByName(String name);
}
