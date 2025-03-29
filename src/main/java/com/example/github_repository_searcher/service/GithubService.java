package com.example.github_repository_searcher.service;

import com.example.github_repository_searcher.dao.IGithubRepository;
import com.example.github_repository_searcher.model.GithubRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
class GithubService implements IGithubService{

    private IGithubRepository githubRepository;

    public GithubService(IGithubRepository githubRepository) {
        this.githubRepository = githubRepository;
    }

    @Override
    public List<GithubRepository> searchRepositoryData(List<Map<String, Object>> items) {
        List<GithubRepository> githubRepositoryList = new ArrayList<>();
        for(Map<String, Object> item: items){
             githubRepositoryList.add(new GithubRepository((Integer) item.get("id"), (String) item.get("name"), (String) item.get("description"), (String)((Map) item.get("owner")).get("login"), (String) item.get("language"), (Integer) item.get("stargazers_count"), (Integer) item.get("forks_count"),Timestamp.from(Instant.parse((CharSequence) item.get("updated_at"))), Timestamp.from(Instant.now())));
        }
        return githubRepository.saveAll(githubRepositoryList);
    }

    @Override
    public List<GithubRepository> getRepositories(String language, Integer minStars, String sort) {
        List<GithubRepository> repositories ;
        if ("stars".equalsIgnoreCase(sort) ) {
            repositories = githubRepository.findByLanguageOrderByStars(language);
        } else if ("forks".equalsIgnoreCase(sort)) {
            repositories = githubRepository.findByLanguageOrderByForksDesc(language);
        } else if ("updated".equalsIgnoreCase(sort)) {
            repositories = githubRepository.findByLanguageOrderByLastUpdatedDesc(language);
        } else {
            repositories = githubRepository.findByLanguageAndStarsGreaterThanEqual(language, minStars);
        }
        return repositories;
    }
}
