package com.lcwd.restaurant.helper;

import com.lcwd.restaurant.dtos.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {


    //U is entity and V is dto
    //                           return  dto of pageableResponse ||  get entity of page
    public static <U,V>PageableResponse<V> getPageableResponse(Page<U> page ,Class<V>type) {
        List<U> entity = page.getContent();
        List<V> dtoList=entity.stream().map(object->new ModelMapper().map(object,type)).collect(Collectors.toList()) ;
        PageableResponse<V> response = new PageableResponse<>();

        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return  response;

    }
}