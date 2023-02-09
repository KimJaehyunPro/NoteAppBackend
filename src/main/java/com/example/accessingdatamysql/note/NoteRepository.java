package com.example.accessingdatamysql.note;
import com.example.accessingdatamysql.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    Page<Note> findAll(Pageable pageable);
    Page<Note> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    @Query("SELECT note FROM Note note JOIN note.tags tag WHERE tag.name = :tag")
    Optional<List<Note>> findAllByTag(@Param("tag")String tag);
    @Query("SELECT note FROM Note note JOIN note.tags tag WHERE tag.name = :tag")
    Page<Note> findAllByTag(@Param("tag")String tag, Pageable pageable);
}
