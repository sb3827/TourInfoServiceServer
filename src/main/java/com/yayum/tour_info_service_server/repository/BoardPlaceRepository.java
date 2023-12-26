package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import com.yayum.tour_info_service_server.entity.BoardPlace;
import com.yayum.tour_info_service_server.entity.BoardPlacePK;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardPlaceRepository extends JpaRepository<BoardPlace, BoardPlacePK> {

    // boardPlace place_pno set null
    @Modifying
    @Transactional
    @Query("Update BoardPlace bp set bp.place.pno = null where bp.place.pno = :pno")
    void updateBoardPlacePno(Long pno);

    @Modifying
    @Transactional
    @Query("delete from BoardPlace bp where bp.place.pno = :pno")
    void removeBoardPlaceByPno(Long pno);

    //게시글에 해당하는것 삭제
    void deleteAllByBoardPlacePKBoard(Board board);

}
