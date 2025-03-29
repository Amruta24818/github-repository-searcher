package com.example.github_repository_searcher.dao;

import com.example.github_repository_searcher.model.GithubRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGithubRepository extends JpaRepository<GithubRepository, Integer> {

    List<GithubRepository> findByLanguageAndStarsGreaterThanEqual(String language, Integer minStars);
    List<GithubRepository> findByLanguageOrderByStars(String language);
    List<GithubRepository> findByLanguageOrderByForksDesc(String language);
    List<GithubRepository> findByLanguageOrderByLastUpdatedDesc(String language);
}
