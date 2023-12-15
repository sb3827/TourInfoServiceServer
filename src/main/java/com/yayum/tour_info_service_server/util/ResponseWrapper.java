package com.yayum.tour_info_service_server.util;

import com.yayum.tour_info_service_server.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseWrapper<T> {
    private boolean result;
    private T data;
}

