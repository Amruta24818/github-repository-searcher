package com.example.github_repository_searcher.dto;

import com.example.github_repository_searcher.Entity.GithubRepository;

import java.util.List;

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
