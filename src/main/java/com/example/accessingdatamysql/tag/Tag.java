package com.example.accessingdatamysql.tag;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

import com.example.accessingdatamysql.note.Note;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private Set<Note> notes = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }
}
