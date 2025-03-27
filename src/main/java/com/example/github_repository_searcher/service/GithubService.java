package com.example.github_repository_searcher.service;

import com.example.github_repository_searcher.Entity.GithubRepository;
import com.example.github_repository_searcher.dao.IGithubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GithubService implements IGithubService{

    @Autowired
    private IGithubRepository githubRepository;

    @Override
    public List<GithubRepository> searchRepositoryData(List<Map<String, Object>> items) {
        List<GithubRepository> githubRepositoryList = new ArrayList<>();
        for(Map<String, Object> item: items){
             githubRepositoryList.add(new GithubRepository((Integer) item.get("id"), (String) item.get("name"), (String) item.get("description"), (String)((Map) item.get("owner")).get("login"), (String) item.get("language"), (Integer) item.get("stargazers_count"), (Integer) item.get("forks_count"),Timestamp.from(Instant.parse((CharSequence) item.get("updated_at")))));
        }
        return githubRepository.saveAll(githubRepositoryList);
    }
}
