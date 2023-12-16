package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.BoardLike;
import com.yayum.tour_info_service_server.entity.BoardLikePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikePK> {
    //게시글에 해당하는 좋아요 삭제
    void deleteAllByBoardLikePKBoard(Board board);
}
