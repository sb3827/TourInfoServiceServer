package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.BoardLike;
import com.yayum.tour_info_service_server.entity.BoardLikePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikePK> {
  @Modifying
  @Query("delete from Reply bl where bl.board.bno=:bno")
  void deleteByBno(Long bno);
}
