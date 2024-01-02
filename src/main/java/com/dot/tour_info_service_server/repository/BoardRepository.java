package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Board;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 게시글이 코스 게시글이면 true, 장소 게시글이면 false

    @Query("select b.isCourse from Board b where b.bno in (select bp.boardPlacePK.board.bno from BoardPlace bp where bp.place.pno = :pno)")
    Boolean boardIsCourse(Long pno);

    // pno로 작성한 게시글 bno 반환
    @Query("select b.bno from Board b where b.bno in (select bp.boardPlacePK.board.bno from BoardPlace bp where bp.place.pno = :pno)")
    List<Long> returnBnos(Long pno);

    // 게시글 삭제
    @Modifying
    @Transactional
    @Query("delete from Board b where b.bno in (select bp.boardPlacePK.board.bno from BoardPlace bp where bp.place.pno = :pno)")
    void removeBoard(Long pno);
}
