package com.example.accessingdatamysql.note;

import com.example.accessingdatamysql.tag.Tag;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String title;

    @Column(columnDefinition = "TEXT", length = 65535)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationTimestamp = LocalDateTime.now();

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastOpenTimestamp = LocalDateTime.now();

    @ManyToMany()
    @JoinTable(
            name = "note_tags",
            joinColumns = { @JoinColumn(name = "note_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id", referencedColumnName = "id") }
    )
    @JsonManagedReference
    private Set<Tag> tags = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public LocalDateTime getLastOpenTimestamp() {
        return lastOpenTimestamp;
    }

    public void setLastOpenTimestamp(LocalDateTime lastOpenTimestamp) {
        this.lastOpenTimestamp = lastOpenTimestamp;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
