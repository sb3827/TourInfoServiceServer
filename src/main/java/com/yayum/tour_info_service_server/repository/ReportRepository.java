package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.dto.ReportFilterDTO;
import com.yayum.tour_info_service_server.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Long> {
    //신고 전체 조회 - 최신순
    List<Report> findAllByOrderByRegDateDesc();

    //신고 필터 조회 - 처리 or 처리X
    @Query("select r " +
            "from Report r left outer join Member m on r.complainant.mno = m.mno " +
            "where r.isDone=:isDone or m.name like concat('%',:name,'%')\n ")
    List<Report> searchIsDone(Boolean isDone,String name);

    //신고 필터 조회 - 게시글(처리 or 처리x)
    @Query("select r " +
            "from Report r left outer join Member m on r.complainant.mno = m.mno " +
            "where r.board is not null or r.isDone=:isDone or m.name like concat('%',:name,'%')\n ")
    List<Report> searchBoardReport(Boolean isDone,String name);

    //신고 필터 조회 - 댓글(처리 or 처리x)
    @Query("select r " +
            "from Report r left outer join Member m on r.complainant.mno = m.mno " +
            "where r.reply is not null or r.isDone=:isDone or m.name like concat('%',:name,'%')\n ")
    List<Report> searchReplyReport(Boolean isDone,String name);

}
