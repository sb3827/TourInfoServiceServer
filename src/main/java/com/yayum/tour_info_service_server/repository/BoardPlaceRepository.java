package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.BoardPlace;
import com.yayum.tour_info_service_server.entity.BoardPlacePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardPlaceRepository extends JpaRepository<BoardPlace, BoardPlacePK> {
  @Modifying
  @Query("delete from Reply bp where bp.board.bno=:bno")
  void deleteByBno(Long bno);
}
