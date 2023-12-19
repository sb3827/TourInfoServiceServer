package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.BoardLike;
import com.yayum.tour_info_service_server.entity.BoardLikePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikePK> {
  @Modifying
  @Transactional
  @Query("Delete from BoardLike bl where bl.boardLikePK.board.bno=:bno")
  void deleteByBno(Long bno);
}
