package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

  // 모든 장소 포스팅 정보 조회
  @Query("SELECT b, m FROM Board b JOIN b.writer m " +
      "WHERE b.isCourse = false AND b.bno =:bno ")
   List<Object[]> getPlaceBoardByBno(@Param("bno") Long bno);

  // 모든 코스 포스팅 정보 조회
  @Query("SELECT b, m FROM Board b JOIN b.writer m " +
      "WHERE b.isCourse = false AND b.bno =:bno ")
  List<Object[]> getCourseBoardByBno(@Param("bno") Long bno);


}
