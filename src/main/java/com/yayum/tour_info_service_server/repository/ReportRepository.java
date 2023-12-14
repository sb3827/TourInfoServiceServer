package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<Report, Long> {

  //board 삭제할때 Report의 bno값을 null값으로 변경시켜줌
  @Modifying
  @Query("UPDATE Report r SET r.board.bno = null where r.board.bno=:bno")
  void updateReportByBoardBno(@Param("bno") Long bno);

}
