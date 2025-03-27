package com.example.github_repository_searcher.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SearchDto {

    private String query;
    private String language;
    private String sort;

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
