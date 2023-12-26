package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Report;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

  //신고 전체 조회 - 최신순
  @Query("select r " +
      "from Report r left outer join Member m on r.defendant_mno = m.mno " +
      "where m.name like concat('%',:search,'%')" +
      "group by r.sno " +
      "order by r.regDate desc ")
  List<Report> searchReportAll(String search);

  //신고 필터 조회 - 처리 or 처리X
  @Query("select r " +
      "from Report r left outer join Member m on r.defendant_mno = m.mno " +
      "where r.isDone=:isDone and m.name like concat('%',:search,'%') " +
      "group by r.sno " +
      "order by r.regDate desc")
  List<Report> searchIsDone(Boolean isDone, String search);

  //신고 필터 조회 - 게시글(처리 or 처리x)
  @Query("select r " +
      "from Report r left outer join Member m on r.defendant_mno = m.mno " +
      "where r.board_bno is not null and r.isDone=:isDone and m.name like concat('%',:search,'%')" +
      "group by r.sno " +
      "order by r.regDate desc ")
  List<Report> searchBoardReport(Boolean isDone, String search);

  //신고 필터 조회 - 댓글(처리 or 처리x)
  @Query("select r " +
      "from Report r left outer join Member m on r.defendant_mno = m.mno " +
      "where r.reply_rno is not null and r.isDone=:isDone and m.name like concat('%',:search,'%') " +
      "group by r.sno " +
      "order by r.regDate desc")
  List<Report> searchReplyReport(Boolean isDone, String search);

  @Modifying
  @Transactional
  @Query("update Report r set r.board_bno = null")
  void updateReportBnoNull(List<Long> bno);

  //board 삭제할때 Report의 bno값을 null값으로 변경시켜줌
  @Modifying
  @Query("UPDATE Report r SET r.board.bno = null where r.board.bno=:bno")
  void updateReportByBoardBno(@Param("bno") Long bno);

}
