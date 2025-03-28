package com.example.github_repository_searcher.dto.common;

public class ErrorResponseDto {
    private Integer statusCode;

    private String message;

    public ErrorResponseDto() {
    }

    public ErrorResponseDto(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponseDto{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
