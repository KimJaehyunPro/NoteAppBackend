package com.example.accessingdatamysql.note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    @Query("SELECT note FROM User user JOIN user.notes note WHERE user.id = :userId")
    List<Note> findAllByUserId(Integer userId);
    @Query("SELECT note FROM User user JOIN user.notes note WHERE user.id = :userId")
    Page<Note> findAllByUserId(Pageable pageable, Integer userId);
    @Query("SELECT note FROM User user " +
            "JOIN user.notes note " +
            "WHERE user.id = :userId AND " +
            "(note.title LIKE %:query% OR note.content LIKE %:query%)")
    Page<Note> findAllByTitleOrContent(String query, Pageable pageable, Integer userId);
    @Query("SELECT note FROM User user JOIN user.notes note JOIN note.tags tag WHERE user.id = :userId AND tag.name = :tagName")
    Set<Note> findAllByTag(@Param("tagName")String tagName, Integer userId);
    @Query("SELECT note FROM User user JOIN user.notes note JOIN note.tags tag WHERE user.id = :userId AND tag.name = :tagName")
    Page<Note> findAllByTag(@Param("tagName")String tagName, Pageable pageable, Integer userId);
}
