package com.assessment.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationApiResponse<T> {
    private String message;
    private T data;
    private boolean success;
    private int page;
    private int size;
    private long totalElements;

    public PaginationApiResponse(String message, T data, boolean success, int page, int size, long totalElements) {
        this.message = message;
        this.data = data;
        this.success = success;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
    }

}
