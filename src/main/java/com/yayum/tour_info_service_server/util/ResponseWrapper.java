package com.yayum.tour_info_service_server.util;

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

