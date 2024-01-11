package com.dot.tour_info_service_server.repository;

import com.dot.tour_info_service_server.entity.Board;
import com.dot.tour_info_service_server.entity.BoardLike;
import com.dot.tour_info_service_server.entity.BoardLikePK;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikePK> {


//    @Query("delete from Image i where i.board.bno in (select bp.boardPlacePK.board.bno from BoardPlace bp where bp.boardPlacePK.place.pno = :pno )")


    @Modifying
    @Transactional
    @Query("delete from BoardLike bl where bl.boardLikePK.board.bno in (select bp.boardPlacePK.board.bno from BoardPlace bp where bp.place.pno = :pno)")
    void removeBoardLike(Long pno);

    //게시글에 해당하는 좋아요 삭제
    void deleteAllByBoardLikePKBoard(Board board);

    @Modifying
    @Transactional
    @Query("Delete from BoardLike bl where bl.boardLikePK.board.bno=:bno")
    void deleteByBno(Long bno);

    @Modifying
    @Transactional
    @Query("delete from BoardLike bl where bl.boardLikePK.member.mno = :mno")
    void removeBoardLIkeByMno(Long mno);

    @Query("select count(bl.boardLikePK.member.mno) from BoardLike bl " +
            "left outer join Board b on b.bno = bl.boardLikePK.board.bno " +
            "where b.bno =:bno")
    List<Object[]> getBoardLikeByBno(Long bno);

    boolean existsByBoardLikePK(BoardLikePK boardLikePK);


}
