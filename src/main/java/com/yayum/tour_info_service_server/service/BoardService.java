package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.CourseBoardDTO;

import java.sql.SQLException;

public interface BoardService {

    // 코스 수정 service logic
    Long modifyCourse(CourseBoardDTO courseBoardDTO) throws IllegalAccessException, SQLException;
}
