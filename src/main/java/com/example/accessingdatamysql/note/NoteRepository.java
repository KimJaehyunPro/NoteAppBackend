package com.example.accessingdatamysql.note;
import org.springframework.data.repository.CrudRepository;
public interface NoteRepository extends CrudRepository<Note, Integer> {
}
