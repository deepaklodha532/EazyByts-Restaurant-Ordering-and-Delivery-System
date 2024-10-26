package com.lcwd.restaurant.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
//@Builder
public class PageableResponse<T> {

    private List<T>   content ;
    private int pageNumber ;
    private int pageSize;
    private long totalElements;
    private int totalPages ;
    private boolean lastPage ;


}
