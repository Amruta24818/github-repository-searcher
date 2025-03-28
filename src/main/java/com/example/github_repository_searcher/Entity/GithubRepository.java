package com.example.github_repository_searcher.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
public class GithubRepository {
    @Id
    private Integer Id;
    @NotBlank
    private String name;
    @NotBlank
    @Column(columnDefinition="text")
    private String description;
    @NotBlank
    private String owner;
    @NotBlank
    private String language;
    @NotNull
    private Integer stars;
    @NotNull
    private Integer forks;
    private Timestamp lastUpdated;


    public GithubRepository() {
    }

    public GithubRepository(Integer id, String name, String description, String owner, String language, Integer stars, Integer forks, Timestamp lastUpdated) {
        Id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.language = language;
        this.stars = stars;
        this.forks = forks;
        this.lastUpdated = lastUpdated;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getForks() {
        return forks;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
