package com.example.accessingdatamysql.tag;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.example.accessingdatamysql.note.Note;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    @JsonBackReference
    private Set<Note> notes = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastOpenTimestamp = LocalDateTime.now();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public LocalDateTime getLastOpenTimestamp() {
        return lastOpenTimestamp;
    }

    public void setLastOpenTimestamp(LocalDateTime lastOpenTimestamp) {
        this.lastOpenTimestamp = lastOpenTimestamp;
    }
}
