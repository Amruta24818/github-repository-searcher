package com.example.github_repository_searcher.controller;

import com.example.github_repository_searcher.Entity.GithubRepository;
import com.example.github_repository_searcher.dto.SearchResponseDto;
import com.example.github_repository_searcher.dto.SearchDto;
import com.example.github_repository_searcher.dto.common.ErrorResponseDto;
import com.example.github_repository_searcher.service.IGithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/github")
public class RepoController {

    private static final String BaseUrl = "https://api.github.com/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IGithubService githubService;

    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchAllRepositories(@RequestBody SearchDto searchDto) {
        String url = BaseUrl + "search/repositories";

        String query = searchDto.getQuery();
        if (searchDto.getLanguage() != null && !searchDto.getLanguage().isEmpty()) {
            query += " language:" + searchDto.getLanguage();
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("q", query);
        params.put("sort", searchDto.getSort());
        try {
            Map<String, Object> response = restTemplate.getForObject(url + "?q={q}&sort={sort}", Map.class, params);
            List<GithubRepository> list = new ArrayList<>();

            if (response != null) {
                List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

                list = githubService.searchRepositoryData(items);
            }
            return new ResponseEntity<>(new SearchResponseDto("Repositories fetched and saved successfully", list),
                    HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDto(0, "Internal server error"));
        }
    }

    @GetMapping(value = "/repositories",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRepositories(@RequestParam(required = false) String language,
                                  @RequestParam(required = false) Integer minStars,
                                  @RequestParam(required = false) String sort) {

        try {
            List<GithubRepository> repositories = githubService.getRepositories(language, minStars, sort);
            if (repositories.isEmpty()) {
                return new ResponseEntity<>( HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>( new SearchResponseDto(repositories), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>( new ErrorResponseDto(0,"Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
