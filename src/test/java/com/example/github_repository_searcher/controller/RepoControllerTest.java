package com.example.github_repository_searcher.controller;

import com.example.github_repository_searcher.model.GithubRepository;
import com.example.github_repository_searcher.dto.SearchDto;
import com.example.github_repository_searcher.service.IGithubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RepoControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private IGithubService githubService;

    @InjectMocks
    private RepoController repoController;

    private MockMvc mockMvc;

    private SearchDto searchDto;
    private List<GithubRepository> mockRepositoryList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(repoController).build();

        searchDto = new SearchDto();
        searchDto.setQuery("spring boot");
        searchDto.setLanguage("Java");
        searchDto.setSort("stars");

        mockRepositoryList = new ArrayList<>();
        mockRepositoryList.add(new GithubRepository(1, "Repo 1", "Description", "owner", "Java", 100, 50, null, Timestamp.from(Instant.now())));
    }

    @Test
    public void testSearchAllRepositoriesSuccess() throws Exception {
        Map<String, Object> mockResponse = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();
        item.put("id", 1);
        item.put("name", "Repo 1");
        items.add(item);
        mockResponse.put("items", items);

        when(restTemplate.getForObject(anyString(), eq(Map.class), anyMap())).thenReturn(mockResponse);

        when(githubService.searchRepositoryData(anyList())).thenReturn(mockRepositoryList);

        mockMvc.perform(post("/api/github/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"query\": \"spring boot\", \"language\": \"Java\", \"sort\": \"stars\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Repositories fetched and saved successfully"))
                .andExpect(jsonPath("$.repositories[0].name").value("Repo 1"));
    }

    @Test
    public void testSearchAllRepositoriesFailure() throws Exception {
        when(restTemplate.getForObject(anyString(), eq(Map.class), anyMap()))
                .thenThrow(new RuntimeException("External API error"));

        mockMvc.perform(post("/api/github/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"query\": \"spring boot\", \"language\": \"Java\", \"sort\": \"stars\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.statusCode").value(0))
                .andExpect(jsonPath("$.message").value("Internal server error"));
    }

    @Test
    public void testGetRepositoriesSuccess() throws Exception {
        when(githubService.getRepositories(anyString(), anyInt(), anyString())).thenReturn(mockRepositoryList);

        mockMvc.perform(get("/api/github/repositories")
                        .param("language", "Java")
                        .param("minStars", "100")
                        .param("sort", "stars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.repositories[0].name").value("Repo 1"));
    }

    @Test
    public void testGetRepositoriesNoContent() throws Exception {
        when(githubService.getRepositories(anyString(), anyInt(), anyString())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/github/repositories")
                        .param("language", "Java")
                        .param("minStars", "100")
                        .param("sort", "stars"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetRepositoriesError() throws Exception {
        when(githubService.getRepositories(anyString(), anyInt(), anyString()))
                .thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(get("/api/github/repositories")
                        .param("language", "Java")
                        .param("minStars", "100")
                        .param("sort", "stars"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.statusCode").value(0))
                .andExpect(jsonPath("$.message").value("Internal server error"));
    }
}
