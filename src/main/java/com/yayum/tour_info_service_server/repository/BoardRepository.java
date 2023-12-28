package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import jakarta.transaction.Transactional;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface BoardRepository extends JpaRepository<Board, Long> {

  @Query("select b.isCourse from Board b where b.bno in (select bp.boardPlacePK.board.bno from BoardPlace bp where bp.place.pno = :pno)")
  Boolean boardIsCourse(Long pno);

  @Query("select b.bno from Board b where b.bno in (select bp.boardPlacePK.board.bno from BoardPlace bp where bp.place.pno = :pno)")
  List<Long> returnBnos(Long pno);

  @Modifying
  @Transactional
  @Query("delete from Board b where b.bno in (select bp.boardPlacePK.board.bno from BoardPlace bp where bp.place.pno = :pno)")
  void removeBoard(Long pno);

  // 장소,코스 포스팅 모든 정보 조회
  @Query("SELECT b FROM Board b WHERE b.bno =:bno ")
  List<Object[]> getPlaceBoardByBno(Long bno);

  Optional<Board> findBoardByBno(Long bno);

  // 메인페이지
  //최근 올라온 장소 게시글 10개
  @Query("select b.bno,b.title,i.src " +
      "from Board b left outer join Image i on(b.bno=i.board.bno) " +
      "order by b.regDate desc " +
      "limit 10 ")
  List<Object[]> recentlyBoard();

  // 가장 많은 추천을 받은 코스 게시글
  @Query("select b.bno,b.title,i.src " +
      "from Board b left outer join Image i on(b.bno=i.board.bno)" +
      "where b.isCourse=true " +
      "order by b.likes desc " +
      "limit 10")
  List<Object[]> mostLikeCourse();


  //팔로워들의 게시글
  @Query("select b.bno,b.title,i.src " +
      "from Board b left outer join Member m on(m.mno=b.writer.mno) " +
      "left outer join Follow f on(f.followPk.member.mno=m.mno) " +
      "left outer join Image i on(b.bno=i.board.bno) " +
      "where f.followPk.follower.mno=:mno " +
      "group by b.bno " +
      "order by b.regDate desc " +
      "limit 10")
  List<Object[]> followerBoard(Long mno);

  //광고 게시글
  @Query("select b.bno,b.title,i.src " +
      "from Board b left outer join Image i on (b.bno=i.board.bno) " +
      "where b.isAd=true " +
      "group by b.bno " +
      "order by b.regDate desc " +
      "limit 10")
  List<Object[]> adBoard();


//   회원별 장소 포스팅 조회
@Query("select b.bno, b.title, count(r.rno), b.regDate from Board b " +
    "left outer join Reply r on b.bno = r.board.bno " +
    "where b.writer.mno = :mno and b.isCourse = false " +
    "group by b.bno")
  List<Object[]> getBoardByMno(@Param("mno") Long mno);

// 장소별 장소 포스팅 조회
  @Query("select bp.place.pno, b.bno, b.title , i.src, count(r.rno), b.writer.mno, b.regDate from Board b " +
          "left outer join Image i on b.bno = i.board.bno " +
          "left outer join Reply r on b.bno = r.board.bno " +
          "left outer join BoardPlace bp on b.bno = bp.boardPlacePK.board.bno " +
          "where bp.place.pno = :pno " +
          "group by b.bno")
  List<Object[]> getBoardByPno(Long pno);

  // 회원별 코스 포스팅 조회
  @Query("select b.bno, b.title, count(r.rno), b.regDate from Board b " +
          "left outer join Reply r on b.bno = r.board.bno " +
          "where b.writer.mno =:mno and b.isCourse = true " +
          "group by b.bno")
  List<Object[]> getCourseBoardByMno(@Param("mno") Long mno);


}
