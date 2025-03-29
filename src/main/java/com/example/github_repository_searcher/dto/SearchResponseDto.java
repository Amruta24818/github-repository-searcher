package com.example.github_repository_searcher.dto;

import com.example.github_repository_searcher.model.GithubRepository;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResponseDto {
    private String message;
    private List<GithubRepository> repositories;

    public SearchResponseDto(String message, List<GithubRepository> repositories) {
        this.message = message;
        this.repositories = repositories;
    }

    public SearchResponseDto(List<GithubRepository> repositories) {
        this.repositories = repositories;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GithubRepository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<GithubRepository> repositories) {
        this.repositories = repositories;
    }
}
