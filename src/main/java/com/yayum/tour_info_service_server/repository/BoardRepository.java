package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b.isCourse from Board b where b.bno in (select bp.boardPlacePK.board.bno from BoardPlace bp where bp.place.pno = :pno)")
    Boolean boardIsCourse(Long pno);

    @Query("select b.bno from Board b where b.bno in (select bp.boardPlacePK.board.bno from BoardPlace bp where bp.place.pno = :pno)")
    List<Long> returnBnos(Long pno);

    @Modifying
    @Transactional
    @Query("delete from Board b where b.bno in (select bp.boardPlacePK.board.bno from BoardPlace bp where bp.place.pno = :pno)")
    void removeBoard(Long pno);

    //  // 모든 장소 포스팅 정보 조회
//  @Query("SELECT b, i FROM Board b left outer JOIN Image i on(b.bno=i.board.bno) " +
//      "WHERE b.isCourse = false AND b.bno =:bno ")
//   List<Object[]> getPlaceBoardByBno(@Param("bno") Long bno);
//
//  // 모든 코스 포스팅 정보 조회
//  @Query("SELECT b, i FROM Board b left outer JOIN Image i on(b.bno=i.board.bno) " +
//      "WHERE b.isCourse = true AND b.bno =:bno ")
//  List<Object[]> getCourseBoardByBno(@Param("bno") Long bno);

//   회원별 장소 포스팅 조회
//  @Query("SELECT b, i FROM Board b left outer JOIN Image i on(b.bno=i.board.bno)" +
//      "WHERE m.mno =:mno ")
//  List<Object[]> getBoardByMno(@Param("mno") Long mno);


}
