package com.example.github_repository_searcher.service;

import com.example.github_repository_searcher.model.GithubRepository;
import com.example.github_repository_searcher.dao.IGithubRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class GithubServiceTest {

    @Mock
    private IGithubRepository githubRepository;

    @InjectMocks
    private GithubService githubService;

    private List<Map<String, Object>> mockItems;
    private List<GithubRepository> mockRepositoryList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Prepare mock data for searchRepositoryData method
        mockItems = new ArrayList<>();
        Map<String, Object> mockItem = new HashMap<>();
        mockItem.put("id", 1);
        mockItem.put("name", "Repo 1");
        mockItem.put("description", "Description 1");
        Map<String, String> owner = new HashMap<>();
        owner.put("login", "owner1");
        mockItem.put("owner", owner);
        mockItem.put("language", "Java");
        mockItem.put("stargazers_count", 100);
        mockItem.put("forks_count", 50);
        mockItem.put("updated_at", "2022-01-01T12:00:00Z");
        mockItems.add(mockItem);

        mockRepositoryList = new ArrayList<>();
        mockRepositoryList.add(new GithubRepository(1, "Repo 1", "Description 1", "owner1", "Java", 100, 50, Timestamp.from(Instant.parse("2022-01-01T12:00:00Z"))));
    }

    @Test
    public void testSearchRepositoryData() {
        Mockito.when(githubRepository.saveAll(ArgumentMatchers.anyList())).thenReturn(mockRepositoryList);

        List<GithubRepository> result = githubService.searchRepositoryData(mockItems);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Repo 1", result.get(0).getName());
        Mockito.verify(githubRepository, Mockito.times(1)).saveAll(ArgumentMatchers.anyList());
    }

    @Test
    public void testGetRepositoriesByStars() {
        String language = "Java";
        Integer minStars = 100;
        List<GithubRepository> mockResponse = new ArrayList<>();
        mockResponse.add(new GithubRepository(1, "Repo 1", "Description 1", "owner1", "Java", 100, 50, Timestamp.from(Instant.parse("2022-01-01T12:00:00Z"))));
        Mockito.when(githubRepository.findByLanguageOrderByStars(language)).thenReturn(mockResponse);

        List<GithubRepository> result = githubService.getRepositories(language, minStars, "stars");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Repo 1", result.get(0).getName());
        Mockito.verify(githubRepository, Mockito.times(1)).findByLanguageOrderByStars(language);
    }

    @Test
    public void testGetRepositoriesByForks() {
        String language = "Java";
        Integer minStars = 100;
        List<GithubRepository> mockResponse = new ArrayList<>();
        mockResponse.add(new GithubRepository(1, "Repo 1", "Description 1", "owner1", "Java", 100, 50, Timestamp.from(Instant.parse("2022-01-01T12:00:00Z"))));
        Mockito.when(githubRepository.findByLanguageOrderByForksDesc(language)).thenReturn(mockResponse);

        List<GithubRepository> result = githubService.getRepositories(language, minStars, "forks");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Repo 1", result.get(0).getName());
        Mockito.verify(githubRepository, Mockito.times(1)).findByLanguageOrderByForksDesc(language);
    }

    @Test
    public void testGetRepositoriesByUpdated() {
        String language = "Java";
        Integer minStars = 100;
        List<GithubRepository> mockResponse = new ArrayList<>();
        mockResponse.add(new GithubRepository(1, "Repo 1", "Description 1", "owner1", "Java", 100, 50, Timestamp.from(Instant.parse("2022-01-01T12:00:00Z"))));
        Mockito.when(githubRepository.findByLanguageOrderByLastUpdatedDesc(language)).thenReturn(mockResponse);

        List<GithubRepository> result = githubService.getRepositories(language, minStars, "updated");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Repo 1", result.get(0).getName());
        Mockito.verify(githubRepository, Mockito.times(1)).findByLanguageOrderByLastUpdatedDesc(language);
    }

    @Test
    public void testGetRepositoriesByStarsGreaterThanEqual() {
        String language = "Java";
        Integer minStars = 100;
        List<GithubRepository> mockResponse = new ArrayList<>();
        mockResponse.add(new GithubRepository(1, "Repo 1", "Description 1", "owner1", "Java", 100, 50, Timestamp.from(Instant.parse("2022-01-01T12:00:00Z"))));
        Mockito.when(githubRepository.findByLanguageAndStarsGreaterThanEqual(language, minStars)).thenReturn(mockResponse);

        List<GithubRepository> result = githubService.getRepositories(language, minStars, "");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Repo 1", result.get(0).getName());
        Mockito.verify(githubRepository, Mockito.times(1)).findByLanguageAndStarsGreaterThanEqual(language, minStars);
    }

    @Test
    public void testGetRepositoriesWithEmptySort() {
        String language = "Java";
        Integer minStars = 100;
        List<GithubRepository> mockResponse = new ArrayList<>();
        mockResponse.add(new GithubRepository(1, "Repo 1", "Description 1", "owner1", "Java", 100, 50, Timestamp.from(Instant.parse("2022-01-01T12:00:00Z"))));
        Mockito.when(githubRepository.findByLanguageAndStarsGreaterThanEqual(language, minStars)).thenReturn(mockResponse);

        List<GithubRepository> result = githubService.getRepositories(language, minStars, "");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Repo 1", result.get(0).getName());
        Mockito.verify(githubRepository, Mockito.times(1)).findByLanguageAndStarsGreaterThanEqual(language, minStars);
    }
}

