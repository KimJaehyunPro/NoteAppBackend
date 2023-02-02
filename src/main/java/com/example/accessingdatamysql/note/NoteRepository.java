package com.example.accessingdatamysql.note;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NoteRepository extends CrudRepository<Note, Integer> {
    Optional<Note> findNoteByTitle(String tagName);
    Optional<Iterable<Note>> findAllByOrderByIdDesc();
}
