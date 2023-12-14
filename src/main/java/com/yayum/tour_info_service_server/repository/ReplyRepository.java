package com.yayum.tour_info_service_server.repository;

import com.yayum.tour_info_service_server.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

  @Modifying // update, delete를 할 때는 무조건 붙인다.
  @Query("delete from Reply r where r.board.bno=:bno")
  void deleteByBno(Long bno);

}
