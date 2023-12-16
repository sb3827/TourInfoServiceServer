package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.BoardPlace;
import com.yayum.tour_info_service_server.entity.BoardPlacePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardPlaceRepository extends JpaRepository<BoardPlace, BoardPlacePK> {
    //게시글에 해당하는것 삭제
    void deleteAllByBoardPlacePKBoard(Board board);
}
