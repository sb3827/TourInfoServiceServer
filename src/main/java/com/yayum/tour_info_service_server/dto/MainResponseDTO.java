package com.yayum.tour_info_service_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class MainResponseDTO<T> {
    private T mostBoardPlace;
    private T recentlyBoard;
    private T mostLikeCourse;
    private T followBoard;
    private T adBoard;
}
