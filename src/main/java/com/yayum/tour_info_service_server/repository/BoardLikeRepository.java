package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.BoardLike;
import com.yayum.tour_info_service_server.entity.BoardLikePK;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikePK> {


//    @Query("delete from Image i where i.board.bno in (select bp.boardPlacePK.board.bno from BoardPlace bp where bp.boardPlacePK.place.pno = :pno )")


    @Modifying
    @Transactional
    @Query("delete from BoardLike bl where bl.boardLikePK.board.bno in (select bp.boardPlacePK.board.bno from BoardPlace bp where bp.place.pno = :pno)")
    void removeBoardLike(Long pno);
}