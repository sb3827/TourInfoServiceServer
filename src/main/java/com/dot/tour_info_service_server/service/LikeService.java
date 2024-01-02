package com.dot.tour_info_service_server.service;

import java.sql.SQLException;

public interface LikeService {
    boolean likeBoard(Long mno, Long bno) throws SQLException;
    boolean unLikeBoard(Long mno, Long bno) throws SQLException;
}
