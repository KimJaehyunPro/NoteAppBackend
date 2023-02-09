package com.example.accessingdatamysql.note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    Page<Note> findAll(Pageable pageable);
    Page<Note> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    @Query("SELECT note FROM Note note JOIN note.tags tag WHERE tag.name = :tag")
    Page<Note> findAllByTag(@Param("tag")String tag, Pageable pageable);
}
