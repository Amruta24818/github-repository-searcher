package com.example.github_repository_searcher.dto;

public class SearchDto {

    private String query;
    private String language;
    private String sort;

    public void setQuery(String query) {
        this.query = query;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getQuery() {
        return query;
    }

    public String getLanguage() {
        return language;
    }

    public String getSort() {
        return sort;
    }
}
