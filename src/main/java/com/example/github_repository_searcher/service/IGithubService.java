package com.example.github_repository_searcher.service;

import com.example.github_repository_searcher.model.GithubRepository;

import java.util.List;
import java.util.Map;

public interface IGithubService {

    public List<GithubRepository> searchRepositoryData(List<Map<String, Object>> items);

    public List<GithubRepository> getRepositories(String language, Integer minStars, String sort);
}
