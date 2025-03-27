package com.example.github_repository_searcher.controller;

import com.example.github_repository_searcher.Entity.GithubRepository;
import com.example.github_repository_searcher.dto.SearchResponseDto;
import com.example.github_repository_searcher.dto.SearchDto;
import com.example.github_repository_searcher.service.IGithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    public ResponseEntity<SearchResponseDto> getAllRepositories(@RequestBody SearchDto searchDto) {
        String url = BaseUrl + "search/repositories";

        String query = searchDto.getQuery();
        if (searchDto.getLanguage() != null && !searchDto.getLanguage().isEmpty()) {
            query += " language:" + searchDto.getLanguage();
        }
        System.out.println("query: " + query);

        HashMap<String, String> params = new HashMap<>();
        params.put("q", query);
        params.put("sort", searchDto.getSort());

        Map<String, Object> response = restTemplate.getForObject(url + "?q={q}&sort={sort}", Map.class, params);
        List<GithubRepository> list = new ArrayList<>();
        if (response != null) {
            List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

            list = githubService.searchRepositoryData(items);
        }
        System.out.println(list);
        return new ResponseEntity <> (new SearchResponseDto("Repositories fetched and saved successfully", list) ,
                HttpStatus.OK);
    }

    


}
