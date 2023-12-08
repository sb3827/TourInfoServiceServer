package com.yayum.tour_info_service_server.service;

import com.yayum.tour_info_service_server.dto.BoardDTO;

import java.util.Map;

public interface BoardService {

  Long register(BoardDTO boardDTO);

}
