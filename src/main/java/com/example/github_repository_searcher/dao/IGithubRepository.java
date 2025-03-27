package com.example.github_repository_searcher.dao;

import com.example.github_repository_searcher.Entity.GithubRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGithubRepository extends JpaRepository<GithubRepository, Long> {
}
