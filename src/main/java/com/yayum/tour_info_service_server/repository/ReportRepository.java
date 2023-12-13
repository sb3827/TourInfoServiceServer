package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.dto.ReportFilterDTO;
import com.yayum.tour_info_service_server.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report,Long> {
    //신고 전체 조회 - 최신순

    @Query("select r " +
            "from Report r left outer join Member m on (r.complainant.mno = m.mno and r.defendant.mno=m.mno) " +
            "where r.complainant.name like concat('%',:search,'%') or r.defendant.name like concat('%',:search,'%')" +
            "group by r.sno " +
            "order by r.regDate desc ")
    List<Report> searchReportAll(String search);


    //신고 필터 조회 - 처리 or 처리X
    @Query("select r " +
            "from Report r left outer join Member m on r.complainant.mno = m.mno " +
            "where r.isDone=:isDone and (r.complainant.name like concat('%',:name,'%') or r.defendant.name like concat('%',:name,'%')) " +
            "order by r.regDate desc")
    List<Report> searchIsDone(Boolean isDone,String name);

    //신고 필터 조회 - 게시글(처리 or 처리x)
    @Query("select r " +
            "from Report r left outer join Member m on r.complainant.mno = m.mno " +
            "where r.board is not null and r.isDone=:isDone and (r.complainant.name like concat('%',:name,'%') or r.defendant.name like concat('%',:name,'%'))" +
            "order by r.regDate desc ")
    List<Report> searchBoardReport(Boolean isDone,String name);

    //신고 필터 조회 - 댓글(처리 or 처리x)
    @Query("select r " +
            "from Report r left outer join Member m on r.complainant.mno = m.mno " +
            "where r.reply is not null and r.isDone=:isDone and (r.complainant.name like concat('%',:name,'%') or r.defendant.name like concat('%',:name,'%')) " +
            "order by r.regDate desc")
    List<Report> searchReplyReport(Boolean isDone,String name);

}
